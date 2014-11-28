package edu.usc.palhunter.business;

import edu.usc.palhunter.db.DBHelper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.sun.xml.internal.bind.v2.schemagen.xmlschema.NoFixedFacet;

import edu.usc.palhunter.db.Notification;
import edu.usc.palhunter.util.Utility;

import java.sql.ResultSet;
import java.sql.SQLException;

public class NotificationManager extends TableManager {

  private static final String TABLE_NAME = "NOTIFICATION";
  private static final String GOOGLE_API_KEY = "AIzaSyAg4wpwDTMnJE7t4FEBux_upfPeFBskTVM";
  public static final String NEAREST_FRIEND ="Nearest friend is ";
  public Notification getNotification(int notificationId) {
    String sql = String.format("select * from %s where notification_id = :id");
    return getHandle().createQuery(sql).bind("id", notificationId)
        .map(new Notification.Mapper()).first();
  }

  public List<Notification> getUserNotification(int userId) {
    String sql = String.format("select * from %s where user_id = :userId",
        TABLE_NAME);
    return getHandle().createQuery(sql).bind("userId", userId)
        .map(new Notification.Mapper()).list();
  }

  public void setRegisterId(int userId, String rid) {

    StringBuilder sb = new StringBuilder();
    sb.append("merge into push_info p ")
        .append("using (select :userId as user_id, :rid as rid from dual) N ")
        .append("on (p.user_id = N.user_id) ")
        .append("when matched then update set p.rid = N.rid ")
        .append("when not matched then insert (user_id, rid) ")
        .append("values (N.user_id, N.rid)");
    getHandle().createStatement(sb.toString()).bind("userId", userId)
        .bind("rid", rid).execute();
    getHandle().commit();
  }
 

  public String sendUserNotification(List<Integer> userIds, String content,
      int type) {
    List<String> regIds = new ArrayList<String>();
    for (int userId : userIds) {
      String regId;
      try {
        regId = getRegistrationId(userId);
        regIds.add(regId);
      } catch (ClassNotFoundException | SQLException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }
    return sendNotification(regIds, content, type);
  }
  
  public String sendNotification(List<String> regIds, String content) {
    return sendNotification(regIds, content, Notification.SYSTEM_MESSAGE);
  }

  public String sendNotification(List<String> regIds, String content, int type) {
    String result = "";
    JSONObject message = new JSONObject();
    try {
      message.put("registration_ids", new JSONArray(regIds));
      JSONObject data = new JSONObject();
      data.put("content", content);
      data.put("type", type);
      message.put("data", data);
      System.out.println(message.toString());
      HttpURLConnection conn = null;
      URL url = new URL("https://android.googleapis.com/gcm/send");
      conn = (HttpURLConnection) url.openConnection();
      conn.setRequestMethod("POST");
      conn.setRequestProperty("Content-Type", "application/json");
      conn.addRequestProperty("Authorization", "key=" + GOOGLE_API_KEY);
      conn.setDoOutput(true);

      OutputStream output = conn.getOutputStream();
      output.write(message.toString().getBytes());
      output.close();
      System.out.println("finish push");
      int responseCode = conn.getResponseCode();
      System.out.println(responseCode);
      result = Utility.streamToString(conn.getInputStream());
    } catch (JSONException | IOException e) {
      e.printStackTrace();
    }
    return result;
  }

  public String getRegistrationId(int userId) throws SQLException, ClassNotFoundException{
      String sql = "select rid from push_info where user_id = "+userId;
      DBHelper db = new DBHelper();
      ResultSet rs = db.executeQuery(sql);
      String rid = "";
      while(rs.next()){
          return rs.getString(1);
      }
      db.close();
      return rid;
  }
  public void saveNotification(Notification noti) throws ClassNotFoundException, SQLException{
      String sql = "insert into notification (user_id, content, time, type) values("+noti.getUserId()+
              ", '"+noti.getContent()+"', CURRENT_TIMESTAMP, "+noti.getType()+")";
      DBHelper db = new DBHelper();
      db.executeQuery(sql);
      db.close();
  }
}

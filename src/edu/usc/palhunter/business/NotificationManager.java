package edu.usc.palhunter.business;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import edu.usc.palhunter.db.Notification;
import edu.usc.palhunter.util.Utility;

public class NotificationManager extends TableManager {

  private static final String TABLE_NAME = "NOTIFICATION";
  private static final String GOOGLE_API_KEY = "AIzaSyAg4wpwDTMnJE7t4FEBux_upfPeFBskTVM";

  public Notification getNotification(int notificationId) {
    String sql = String.format("select * from %s where notification_id = :id");
    return getHandle().createQuery(sql).bind("id", notificationId)
        .map(new Notification.Mapper()).first();
  }

  public List<Notification> getUsrNotification(int userId) {
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

  public String sendNotification(List<String> regIds, String content) {
    String result = "";
    JSONObject message = new JSONObject();
    try {
      message.put("registration_ids", new JSONArray(regIds));
      JSONObject data = new JSONObject();
      data.put("content", content);
      message.put("data", data);
      System.out.println(message.toString());
      HttpURLConnection conn = null;
      URL url = new URL("https://android.googleapis.com/gcm/send");
      conn = (HttpURLConnection) url.openConnection();
      conn.setRequestMethod("POST");
      conn.setRequestProperty("Content-Type", "application/json");
      conn.addRequestProperty("Authorization", "key="
          + GOOGLE_API_KEY);
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

}

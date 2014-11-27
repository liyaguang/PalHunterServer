package edu.usc.palhunter.business;

import java.sql.SQLException;
import java.util.List;

import org.skife.jdbi.v2.sqlobject.mixins.GetHandle;

import edu.usc.infolab.roadnetwork.IGeoPoint;
import edu.usc.palhunter.db.DBHelper;
import edu.usc.palhunter.db.TrjPoint;
import java.sql.ResultSet;
import edu.usc.palhunter.db.User;

public class TrajectoryManager extends TableManager {

  private static final String TABLE_NAME = "TRAJECTORY";
  private static final String CURRENT_LOCATION_TABLE = "CURRENT_LOCATION";

  public void updateLocation(int userId, IGeoPoint point)
      throws ClassNotFoundException, SQLException {

    DBHelper db = getDB();

    String sql = "insert into TRAJECTORY (time, lat, lng, user_id, location) values (CURRENT_TIMESTAMP, "
        + point.getLat()
        + ","
        + point.getLng()
        + ","
        + userId
        + ","
        + "SDO_GEOMETRY(2001, 8307, \n"
        + "    SDO_POINT_TYPE("
        + point.getLat() + "," + point.getLng() + ",NULL), NULL, NULL) );";

    db.executeQuery(sql);

    /*
     * Find nearest neighbor before update
     */
    String nearestNeighbor = "select /*+ordered*/ user_id, sdo_nn_distance (1) distance from current_location where user_id <> "
        + userId
        + " and sdo_nn(location, \n"
        + "(select location from current_location where user_id = "
        + userId
        + "),'sdo_num_res=3',1)='TRUE'  ORDER BY distance ";

    ResultSet rs = db.executeQuery(nearestNeighbor);
    int neighborId = 0;
    while (rs.next()) {
      neighborId = rs.getInt(1);
      if (neighborId > 0) {
        break;
      }
    }
    System.out.println("neighbor id is :" + neighborId);
    /*
     * update current location
     */
    String updateCurrentLocation = "declare exist number;\n"
        + "begin\n"
        + "select count(user_id) into exist from current_location where user_id = "
        + userId
        + ";\n"
        + "if exist > 0 then\n"
        + "update current_location set attime = CURRENT_TIMESTAMP, location = SDO_GEOMETRY(2001, 8307, \n"
        + "    SDO_POINT_TYPE("
        + point.getLat()
        + ","
        + point.getLng()
        + ",NULL), NULL, NULL) where user_id = "
        + userId
        + ";\n"
        + "    else \n"
        + "    insert into current_location (user_id, location, attime) values (\n"
        + "    "
        + userId
        + ", SDO_GEOMETRY(2001, 8307, \n"
        + "    SDO_POINT_TYPE("
        + point.getLat()
        + ","
        + point.getLng()
        + ",NULL), NULL, NULL), CURRENT_TIMESTAMP\n"
        + "    );\n"
        + "    end if;\n" + "end;";
    db.executeQuery(updateCurrentLocation);

    /*
     * Find new nearest neighbor id
     */
    rs = db.executeQuery(nearestNeighbor);
    int newNeighborId = 0;
    while (rs.next()) {
      newNeighborId = rs.getInt(1);
      if (newNeighborId > 0)
        break;
    }
    System.out.println("New neighbor id is :" + newNeighborId);

    /*
     * send notification
     */
    if (newNeighborId != neighborId) {
      // notify
      User user = (new UserManager()).getUser(newNeighborId);
      System.out.println("new neighbor name: " + user.getNick());
      NotificationManager notimanager = new NotificationManager();
      // notimanager.sendNotification(null, sql)
    }

  }

  public List<TrjPoint> getTrjPoints(int startPointId, int endPointId) {
    String sql = String
        .format(
            "select * from %s where point_id >= :startPointId and point_id <= :endPointId",
            TABLE_NAME);
    List<TrjPoint> points = getHandle().createQuery(sql)
        .bind("startPointId", startPointId).bind("endPointId", endPointId)
        .map(new TrjPoint.Mapper()).list();
    return points;
  }
}

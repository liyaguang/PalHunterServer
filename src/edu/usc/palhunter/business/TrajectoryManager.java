package edu.usc.palhunter.business;

import java.sql.SQLException;
import java.util.List;

import org.skife.jdbi.v2.sqlobject.mixins.GetHandle;

import edu.usc.infolab.roadnetwork.IGeoPoint;
import edu.usc.palhunter.db.DBHelper;
import edu.usc.palhunter.db.TrjPoint;

public class TrajectoryManager extends TableManager {

  private static final String TABLE_NAME = "TRAJECTORY";
  private static final String CURRENT_LOCATION_TABLE = "CURRENT_LOCATION";

  public void updateLocation(int userId, IGeoPoint point)
      throws ClassNotFoundException, SQLException {
    DBHelper db = getDB();
    // String sql
    // ="Insert into "+TABLE_NAME+"(time, lat, lng, user_id, location) values(CURRENT_TIMESTAMP,\n"
    // +point.getLat()+ ","+
    // point.getLng()+", "+userId+", SDO_GEOMETRY(2001, 8307, \n"
    // +
    // "    SDO_POINT_TYPE("+point.getLat()+","+point.getLng()+",NULL), NULL, NULL))"
    // ;
    // db.executeQuery(sql);

    // String sql = "declare exist number; begin "+
    // "select count(user_id) into exist  from TRAJECTORY where user_id = "+userId+
    // ";"+
    // " if exist > 0 then "+
    // "update TRAJECTORY set time =CURRENT_TIMESTAMP, lat= "+point.getLat()+","+
    // "lng = "+point.getLng()+", location=SDO_GEOMETRY(2001, 8307, \n"
    // +
    // "    SDO_POINT_TYPE("+point.getLat()+","+point.getLng()+",NULL), NULL, NULL) where user_id = "+userId+";\n"
    // + "else \n"+
    // "insert into TRAJECTORY (time, lat, lng, user_id, location) values (CURRENT_TIMESTAMP, "
    // +
    // point.getLat()+","+point.getLng()+","+userId+","+"SDO_GEOMETRY(2001, 8307, \n"
    // +
    // "    SDO_POINT_TYPE("+point.getLat()+","+point.getLng()+",NULL), NULL, NULL) );"
    // + "    end if;\n"
    // + "end;";

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

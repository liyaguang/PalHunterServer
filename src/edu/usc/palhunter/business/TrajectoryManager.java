package edu.usc.palhunter.business;

import java.sql.SQLException;


import edu.usc.infolab.roadnetwork.IGeoPoint;
import edu.usc.palhunter.db.DBHelper;

public class TrajectoryManager {
  private DBHelper db = new DBHelper();
  private static final String TABLE_NAME = "TRAJECTORY";

  public void updateLocation(int userId, IGeoPoint point)
      throws ClassNotFoundException, SQLException {
    String sql = String.format("insert into %s(lat, lng) values(?, ?)",
        TABLE_NAME, point.getLat(), point.getLng());
    db.executeQuery(sql);
  }
}

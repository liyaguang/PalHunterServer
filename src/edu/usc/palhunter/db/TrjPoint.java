package edu.usc.palhunter.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

public class TrjPoint {
  private int pointId;
  private int userId;
  private double lat;
  private double lng;
  private Timestamp time;

  public int getPointId() {
    return this.pointId;
  }

  public int getUserId() {
    return this.userId;
  }

  public double getLat() {
    return this.lat;
  }

  public double getLng() {
    return this.lng;
  }

  public Timestamp getTime() {
    return this.time;
  }

  public TrjPoint() {

  }

  public TrjPoint(int pointId, int userId, double lat, double lng,
      Timestamp time) {
    this.pointId = pointId;
    this.userId = userId;
    this.lat = lat;
    this.lng = lng;
    this.time = time;
  }

  public static class Mapper implements ResultSetMapper<TrjPoint> {

    @Override
    public TrjPoint map(int index, ResultSet r, StatementContext ctx)
        throws SQLException {
      // TODO Auto-generated method stub
      int pointId = r.getInt("point_id");
      int userId = r.getInt("user_id");
      double lat = r.getDouble("lat");
      double lng = r.getDouble("lng");
      Timestamp time = r.getTimestamp("time");
      return new TrjPoint(pointId, userId, lat, lng, time);
    }

  }

}

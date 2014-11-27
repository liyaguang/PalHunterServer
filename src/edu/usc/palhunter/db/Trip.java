package edu.usc.palhunter.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

public class Trip {

  private int id;
  private int userId;
  private int startPointId;
  private int endPointId;
  private double distance;
  private double calorie;
  private Timestamp time;
  private int steps;
  private String info;

  public int getId() {
    return this.id;
  }

  public int getUserId() {
    return this.userId;
  }

  public int getStartPointId() {
    return this.startPointId;
  }

  public int getEndPointId() {
    return this.endPointId;
  }

  public double getDistance() {
    return this.distance;
  }

  public double getCalorie() {
    return this.calorie;
  }

  public Timestamp getTime() {
    return this.time;
  }

  public String getInfo() {
    return this.info;
  }

  public int getSteps() {
    return this.steps;
  }

  public Trip() {

  }

  public Trip(int id, int userId, int startPointId, int endPointId,
      double distance, double calorie, int steps, Timestamp time, String info) {
    this.id = id;
    this.userId = userId;
    this.startPointId = startPointId;
    this.endPointId = endPointId;
    this.distance = distance;
    this.calorie = calorie;
    this.time = time;
    this.info = info;
    this.steps = steps;
  }

  public static class Mapper implements ResultSetMapper<Trip> {

    @Override
    public Trip map(int index, ResultSet r, StatementContext ctx)
        throws SQLException {
      int id = r.getInt("trip_id");
      int userId = r.getInt("userId");
      double distance = r.getDouble("distance");
      double calorie = r.getDouble("calories");
      Timestamp time = r.getTimestamp("time");
      String info = r.getString("info");
      int steps = r.getInt("steps");
      int startPointId = r.getInt("startPointId");
      int endPointId = r.getInt("endPointId");
      return new Trip(id, userId, startPointId, endPointId, distance, calorie,
          steps, time, info);
    }

  }
}

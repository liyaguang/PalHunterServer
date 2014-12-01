package edu.usc.palhunter.business;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import oracle.jdbc.OracleTypes;
import edu.usc.palhunter.db.Trip;

public class TripManager extends TableManager {
  private final static String TABLE_NAME = "TRIPS";

  public Trip getTrip(int tripId) {

    String sql = String.format("select * from %s where trip_id = :tripId",
        TABLE_NAME);
    Trip trip = getHandle().createQuery(sql).bind("tripId", tripId)
        .map(new Trip.Mapper()).first();
    return trip;
  }

  public List<Trip> getUserTrips(int userId) {
    String sql = String.format("select * from %s where user_id = :userId order by trip_id desc",
        TABLE_NAME);
    List<Trip> trips = getHandle().createQuery(sql).bind("userId", userId)
        .map(new Trip.Mapper()).list();
    return trips;
  }

  public int startTrip(int userId) {
    StringBuilder sql = new StringBuilder();
    int tripId = 0;
    sql.append("begin\n")
        .append(
            "insert into trips(user_id) values(?) returning trip_id into ?;")
        .append("end;");
    try {
      Connection conn = getConnection();
      CallableStatement cs = conn.prepareCall(sql.toString());
      cs.setInt(1, userId);
      cs.registerOutParameter(2, OracleTypes.NUMBER);
      cs.execute();
      tripId = cs.getInt(2);
      conn.close();
    } catch (ClassNotFoundException | SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return tripId;
  }

  public void endTrip(int userId, int tripId, double distance, long duration,
      double calorie, int steps, String info) {
    StringBuilder sql = new StringBuilder();
    sql.append("declare\n")
        .append("max_pid number;min_pid number;")
        .append("begin\n")
        .append(
            "select min(point_id), max(point_id) into min_pid, max_pid from trajectory ")
        .append("where time > (select start_time from trips where trip_id = ?); ")
        .append("update trips set start_point_id = min_pid, end_point_id=max_pid, ")
        .append("duration = ?, steps=?, distance=?, calorie=?, info=? where trip_id=?;")
        .append("end;");
    try {
      Connection conn = getConnection();
      CallableStatement cs = conn.prepareCall(sql.toString());
      cs.setInt(1, tripId);
      cs.setLong(2, duration);
      cs.setInt(3, steps);
      cs.setDouble(4, distance);
      cs.setDouble(5, calorie);
      cs.setString(6, info);
      cs.setInt(7, tripId);
      cs.execute();
      conn.close();
    } catch (ClassNotFoundException | SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
}

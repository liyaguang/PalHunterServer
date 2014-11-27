package edu.usc.palhunter.business;

import java.util.List;

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
    String sql = String.format("select * from %s where user_id = :userId",
        TABLE_NAME);
    List<Trip> trips = getHandle().createQuery(sql).bind("userId", userId)
        .map(new Trip.Mapper()).list();
    return trips;
  }
}

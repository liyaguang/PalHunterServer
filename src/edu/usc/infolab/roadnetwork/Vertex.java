package edu.usc.infolab.roadnetwork;

public class Vertex implements IGeoPoint {

  private int nodeId;
  private double lat;
  private double lng;

  public Vertex(int nodeId, double lat, double lng) {
    this.nodeId = nodeId;
    this.lat = lat;
    this.lng = lng;
  }

  public int getId() {
    return nodeId;
  }

  @Override
  public double getLat() {
    // TODO Auto-generated method stub
    return this.lat;
  }

  @Override
  public double getLng() {
    // TODO Auto-generated method stub
    return this.lng;
  }

  public String toString() {
    return String.format("n%d,%.6f,%.6f", this.nodeId, this.lat, this.lng);
  }
}

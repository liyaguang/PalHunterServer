package edu.usc.palhunter.apis;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import edu.usc.palhunter.business.TrajectoryManager;
import edu.usc.palhunter.business.TripManager;
import edu.usc.palhunter.db.Trip;
import edu.usc.palhunter.db.TrjPoint;

/**
 * Servlet implementation class GetTrip
 */
@WebServlet("/apis/GetTrip")
public class GetTrip extends HttpServlet {
  private static final long serialVersionUID = 1L;

  /**
   * @see HttpServlet#HttpServlet()
   */
  public GetTrip() {
    super();
    // TODO Auto-generated constructor stub
  }

  /**
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
   *      response)
   */
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    String tripIdStr = request.getParameter("tripId").trim();
    int tripId = Integer.parseInt(tripIdStr);
    TripManager manager = new TripManager();
    Trip trip = manager.getTrip(tripId);
    manager.close();
    JSONObject jsonTrip = trip.toJSONObject();
    TrajectoryManager trjManager = new TrajectoryManager();
    List<TrjPoint> points = trjManager.getTrjPoints(trip.getStartPointId(),
        trip.getEndPointId());
    trjManager.close();
    JSONArray jsonPoints = new JSONArray();
    for (TrjPoint point : points) {
      jsonPoints.put(point.toJSONObject());
    }
    try {
      jsonTrip.put("points", jsonPoints);
    } catch (JSONException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    response.getWriter().print(jsonTrip.toString());
  }

  /**
   * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
   *      response)
   */
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    // TODO Auto-generated method stub
  }
}

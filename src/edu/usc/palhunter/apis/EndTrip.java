package edu.usc.palhunter.apis;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import edu.usc.palhunter.business.TripManager;
import edu.usc.palhunter.util.Utility.RequestHelper;

/**
 * Servlet implementation class EndTrip
 */
@WebServlet("/apis/EndTrip")
public class EndTrip extends HttpServlet {
  private static final long serialVersionUID = 1L;

  /**
   * @see HttpServlet#HttpServlet()
   */
  public EndTrip() {
    super();
    // TODO Auto-generated constructor stub
  }

  /**
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
   *      response)
   */
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    // TODO Auto-generated method stub
  }

  /**
   * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
   *      response)
   */
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    RequestHelper helper = new RequestHelper(request);
    JSONObject data = helper.getJSONObject("data");
    try {
      int userId = data.getInt("userId");
      int tripId = data.getInt("tripId");
      double calorie = data.getDouble("calorie");
      double distance = data.getDouble("distance");
      int steps = data.getInt("steps");
      long duration = data.getLong("duration");
      String info = data.getString("info");
      TripManager manager = new TripManager();
      manager.endTrip(userId, tripId, distance, duration, calorie, steps, info);
      manager.close();
    } catch (JSONException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

  }

}

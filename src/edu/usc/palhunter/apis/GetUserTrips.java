package edu.usc.palhunter.apis;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import edu.usc.palhunter.business.TripManager;
import edu.usc.palhunter.db.Trip;

/**
 * Servlet implementation class GetUserTrips
 */
@WebServlet("/apis/GetUserTrips")
public class GetUserTrips extends HttpServlet {
  private static final long serialVersionUID = 1L;

  /**
   * @see HttpServlet#HttpServlet()
   */
  public GetUserTrips() {
    super();
    // TODO Auto-generated constructor stub
  }

  /**
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
   *      response)
   */
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    String userIdStr = request.getParameter("userId").trim();
    int userId = Integer.parseInt(userIdStr);
    TripManager manager = new TripManager();
    List<Trip> trips = manager.getUserTrips(userId);
    JSONArray jsonTrips = new JSONArray();
    for (Trip t : trips) {
      jsonTrips.put(t.toJSONObject());
    }
    manager.close();
    response.getWriter().print(jsonTrips.toString());
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

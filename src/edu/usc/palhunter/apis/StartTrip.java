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

/**
 * Servlet implementation class StartTrip
 */
@WebServlet("/apis/StartTrip")
public class StartTrip extends HttpServlet {
  private static final long serialVersionUID = 1L;

  /**
   * @see HttpServlet#HttpServlet()
   */
  public StartTrip() {
    super();
    // TODO Auto-generated constructor stub
  }

  /**
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
   *      response)
   */
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
  }

  /**
   * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
   *      response)
   */
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    int userId = Integer.parseInt(request.getParameter("userId").trim());
    TripManager manager = new TripManager();
    int tripId = manager.startTrip(userId);
    manager.close();
    JSONObject result = new JSONObject();
    try {
      result.put("tripId", tripId);
    } catch (JSONException exc) {
      exc.printStackTrace();
    }
    response.getWriter().print(result.toString());
  }

}

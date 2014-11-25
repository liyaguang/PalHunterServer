package edu.usc.palhunter.apis;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import edu.usc.infolab.roadnetwork.GeoPoint;
import edu.usc.infolab.roadnetwork.IGeoPoint;
import edu.usc.palhunter.business.TrajectoryManager;

/**
 * Servlet implementation class LocationUpdate
 */
@WebServlet("/apis/LocationUpdate")
public class LocationUpdate extends HttpServlet {
  private static final long serialVersionUID = 1L;

  /**
   * @see HttpServlet#HttpServlet()
   */
  public LocationUpdate() {
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
    try {
      JSONObject param = new JSONObject(request.getParameter("info"));
      double lat = param.getDouble("lat");
      double lng = param.getDouble("lng");
      int userId = param.getInt("userId");
      TrajectoryManager manager = new TrajectoryManager();
      IGeoPoint point = new GeoPoint(lat, lng);
      manager.updateLocation(userId, point);
    } catch (JSONException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

}

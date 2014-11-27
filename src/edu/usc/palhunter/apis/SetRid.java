package edu.usc.palhunter.apis;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.usc.palhunter.business.NotificationManager;

/**
 * Servlet implementation class SetRid
 */
@WebServlet("/apis/SetRid")
public class SetRid extends HttpServlet {
  private static final long serialVersionUID = 1L;

  /**
   * @see HttpServlet#HttpServlet()
   */
  public SetRid() {
    super();
    // TODO Auto-generated constructor stub
  }

  /**
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
   *      response)
   */
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	  String rid = request.getParameter("rid");
    int userId = Integer.parseInt(request.getParameter("userId").trim());
    NotificationManager manager = new NotificationManager();
    manager.setRegisterId(userId, rid);
    manager.close();
	}

  /**
   * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
   *      response)
   */
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    // TODO Auto-generated method stub
    String rid = request.getParameter("rid");
    int userId = Integer.parseInt(request.getParameter("userId").trim());
    NotificationManager manager = new NotificationManager();
    manager.setRegisterId(userId, rid);
    manager.close();
  }

}

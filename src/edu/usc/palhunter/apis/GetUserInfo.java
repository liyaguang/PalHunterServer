package edu.usc.palhunter.apis;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import edu.usc.palhunter.business.UserManager;
import edu.usc.palhunter.db.User;

/**
 * Servlet implementation class GetUserInfo
 */
@WebServlet(description = "Get User Info", urlPatterns = { "/apis/GetUserInfo" })
public class GetUserInfo extends HttpServlet {
  private static final long serialVersionUID = 1L;

  /**
   * @see HttpServlet#HttpServlet()
   */
  public GetUserInfo() {
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
    PrintWriter pw = response.getWriter();
    // pw.println("Welcome to PalHunter!");
    // String fileName = "/WEB-INF/dbConfig.xml";
    // pw.println(request.getSession().getServletContext().getRealPath(fileName));
    JSONObject result = new JSONObject();
    UserManager userManager = new UserManager();

    int userId = Integer.parseInt(request.getParameter("userId").trim());
    User user = userManager.getUser(userId);
    try {
      result.put("userName", user.getNick());
    } catch (JSONException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    response.getWriter().print(result.toString());

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

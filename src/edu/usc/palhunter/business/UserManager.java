package edu.usc.palhunter.business;

import java.sql.ResultSet;
import java.sql.SQLException;

import edu.usc.palhunter.db.DBHelper;

public class UserManager {
  private DBHelper db = new DBHelper();
  private static final String TABLE_NAME = "USERS";

  public String getUser(int userId) {
    String sql = String.format("select * from %s where user_id = %d", TABLE_NAME,
        userId);
    String result = "";
    try {
      ResultSet rs = db.executeQuery(sql);
      rs.next();
      result = rs.getString("USER_NAME");
    } catch (ClassNotFoundException | SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return result;

  }
}

package edu.usc.palhunter.db;

import java.sql.*;

public class ConnectionTester {

  public static void main(String[] args) {
    // TODO Auto-generated method stub
    testConn();

  }

  public static void testConn() {
    DBHelper dbHelper = new DBHelper();
    String sql = "select * from USERS";
    try {
      ResultSet rs = dbHelper.executeQuery(sql);
      while (rs.next()) {
        String userName = rs.getString("USER_NAME");
        System.out.println(userName);
      }
    } catch (ClassNotFoundException | SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

}

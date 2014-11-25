package edu.usc.palhunter.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.skife.jdbi.v2.Handle;
import org.skife.jdbi.v2.util.IntegerMapper;

public class ConnectionTester {

  public static void main(String[] args) {
    // TODO Auto-generated method stub
//    testConn();
    jdbiTest();

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

  public static void jdbiTest() {
    DBHelper dbHelper = new DBHelper();
    Handle h = dbHelper.getHandle();
    List<Integer> ids = h.createQuery("select USER_ID from USERS").bind("id", 0)
        .map(IntegerMapper.FIRST).list();
    for (int id:ids) {
      System.out.println(id);
    }
  }

}

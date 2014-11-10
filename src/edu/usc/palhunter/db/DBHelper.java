package edu.usc.palhunter.db;

import java.sql.*;

public class DBHelper {

  public static final String CLASS_NAME = "oracle.jdbc.driver.OracleDriver";
  Connection conn_ = null;
  private String userName = "team2";
  private String password = "team2";
  private String IP = "geodb.usc.edu";
  private String dbName = "GEODBS";
  private int port = 1521;

  public DBHelper() {

  }

  public DBHelper(String IP, String userName, String password, int port) {
    this.IP = IP;
    this.userName = userName;
    this.password = password;
    this.port = port;
  }

  private static String getConnectionString(String IP, int port, String dbName) {

    String connStr = String.format("jdbc:oracle:thin:@%s:%d:%s", IP, port,
        dbName);
    return connStr;
  }

  public Connection getConnection() throws ClassNotFoundException, SQLException {
    if (conn_ == null) {
      Class.forName(CLASS_NAME);
      String connStr = getConnectionString(IP, port, dbName);
      conn_ = DriverManager.getConnection(connStr, userName, password);
    }
    return conn_;
  }
  public ResultSet executeQuery(String sql) throws ClassNotFoundException, SQLException {
    ResultSet rs = null;
    Connection conn = getConnection();
    PreparedStatement psmt = conn.prepareStatement(sql);
    rs = psmt.executeQuery();
    return rs;
  }
}

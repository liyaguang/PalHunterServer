package edu.usc.palhunter.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;

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

  public Handle getHandle() {
    try {
      Class.forName(CLASS_NAME);
    } catch (ClassNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    String connStr = getConnectionString(IP, port, dbName);
    DBI dbi = new DBI(connStr, userName, password);
    Handle h = dbi.open();
    return h;
  }
  public Connection getConnection() throws ClassNotFoundException, SQLException {
    if (conn_ == null) {
      Class.forName(CLASS_NAME);
      String connStr = getConnectionString(IP, port, dbName);
      conn_ = DriverManager.getConnection(connStr, userName, password);
    }
    return conn_;
  }

  public ResultSet executeQuery(String sql) throws ClassNotFoundException,
      SQLException {
    ResultSet rs = null;
    Connection conn = getConnection();
    PreparedStatement psmt = conn.prepareStatement(sql);
    rs = psmt.executeQuery();
    return rs;
  }

  public void executeUpdate(String sql) throws ClassNotFoundException, SQLException {
    Connection conn = getConnection();
    PreparedStatement psmt = conn.prepareStatement(sql);
    psmt.execute();
  }
  public void close() {
    if (conn_ != null) {
      try {
        conn_.close();
      } catch (SQLException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      conn_ = null;
    }
  }
}

package edu.usc.palhunter.business;

import java.sql.Connection;
import java.sql.SQLException;

import org.skife.jdbi.v2.Handle;

import edu.usc.palhunter.db.DBHelper;

public class TableManager {
  private DBHelper db = null;

  public Connection getConnection() throws ClassNotFoundException, SQLException {
    return getDB().getConnection();
  }

  public Handle getHandle() {
    return getDB().getHandle();
  }

  public DBHelper getDB() {
    if (db == null) {
      db = new DBHelper();
    }
    return db;
  }

  public void close() {
    if (db != null) {
      db.close();
      db = null;
    }
  }
}

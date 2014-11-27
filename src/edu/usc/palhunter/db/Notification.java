package edu.usc.palhunter.db;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class Notification {
  private int id;
  private String content;
  private int userId;
  private Timestamp time;
  private int type;

  public Notification(int id, int userId, String content, Timestamp time,
      int type) {
    this.id = id;
    this.content = content;
    this.userId = userId;
    this.time = time;
    this.type = type;
  }

  public int getId() {
    return this.id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getUserId() {
    return this.userId;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }

  public String getContent() {
    return this.content;

  }

  public void setContent(String content) {
    this.content = content;
  }

  public Timestamp getTime() {
    return this.time;

  }

  public void setTime(Timestamp time) {
    this.time = time;
  }

  public int getType() {
    return this.type;
  }

  public void setType(int type) {
    this.type = type;
  }

  public static class Mapper implements ResultSetMapper<Notification> {

    @Override
    public Notification map(int index, ResultSet r, StatementContext ctx)
        throws SQLException {
      // TODO Auto-generated method stub
      int id = r.getInt("notification_id");
      int userId = r.getInt("userId");
      String content = r.getString("content");
      int type = r.getInt("type");
      Timestamp time = r.getTimestamp("time");
      return new Notification(id, userId, content, time, type);
    }

  }
}
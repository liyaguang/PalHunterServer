package edu.usc.palhunter.db;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

public class User {
  private int userId;
  private String email;
  private String nick;
  private String password;
  private String avatar;
  private String facebookId;

  public User(int userId, String email, String nick, String avatar,
      String facebookId) {
    this.userId = userId;
    this.email = email;
    this.nick = nick;
    this.facebookId = facebookId;
    this.avatar = avatar;
  }

  public User() {

  }

  public int getUserId() {
    return userId;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }

  public String getEmail() {
    return this.email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getNick() {
    return this.nick;
  }

  public void setNick(String nick) {
    this.nick = nick;
  }

  public String getAvatar() {
    return this.avatar;
  }

  public void setAvatar(String avatar) {
    this.avatar = avatar;
  }

  public String getFacebookId() {
    return this.facebookId;
  }

  public void setFacebookId(String facebookId) {
    this.facebookId = facebookId;
  }

  public String toString() {
    return String.format("Id:%d, Email:%s", getUserId(), getEmail());
  }

  public static class Mapper implements ResultSetMapper<User> {

    @Override
    public User map(int index, ResultSet r, StatementContext ctx)
        throws SQLException {
      // TODO Auto-generated method stub
      int userId = r.getInt("user_id");
      String email = r.getString("email");
      String nick = r.getString("nick");
      String facebookId = r.getString("facebook_id");
      String avatar = r.getString("avatar");
      return new User(userId, email, nick, avatar, facebookId);
    }

  }
}

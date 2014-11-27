package edu.usc.palhunter.business;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import edu.usc.palhunter.db.User;


public class UserManager extends TableManager {
  private static final String TABLE_NAME = "USERS";

  public User getUser(int userId) {
    String sql = String
        .format(
            "select user_id, email, facebook_id, nick, avatar from %s where user_id = :userId",
            TABLE_NAME);
    User user = getHandle().createQuery(sql).bind("userId", userId)
        .map(new User.Mapper()).first();
    return user;
  }

}

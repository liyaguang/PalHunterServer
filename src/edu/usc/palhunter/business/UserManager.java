package edu.usc.palhunter.business;

import java.util.List;

import org.skife.jdbi.v2.util.IntegerMapper;

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

  public void addFriendship(int userId1, int userId2) {
    StringBuilder sb = new StringBuilder();
    sb.append("declare c number;\n")
        .append("begin\n")
        .append(
            "select count(*) into c from friends where from_uid = :uid1 and to_uid = :uid2;\n")
        .append("if c = 0 then \n")
        .append("insert into friends(from_uid, to_uid) values(:uid1, :uid2);\n")
        .append("insert into friends(from_uid, to_uid) values(:uid2, :uid1);\n")
        .append("end if;\n").append("end;");
    getHandle().createStatement(sb.toString()).bind("uid1", userId1)
        .bind("uid2", userId2).execute();
    getHandle().commit();
  }

  public void removeFriendship(int uid1, int uid2) {
    StringBuilder sb = new StringBuilder();
    sb.append("begin\n")
        .append(
            "delete from friends where from_uid = :uid1 and to_uid = :uid2;\n")
        .append(
            "delete from friends where from_uid = :uid2 and to_uid = :uid1; \n")
        .append("end;");
    getHandle().createStatement(sb.toString()).bind("uid1", uid1)
        .bind("uid2", uid2).execute();
  }

  public List<User> getFriends(int userId) {
    StringBuilder sql = new StringBuilder();
    sql.append("select * from users u, friends f \n").append(
        "where u.user_id = to_uid and from_uid = :userId\n");
    List<User> friends = getHandle().createQuery(sql.toString())
        .bind("userId", userId).map(new User.Mapper()).list();
    return friends;
  }
}

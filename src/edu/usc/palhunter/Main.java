package edu.usc.palhunter;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import edu.usc.palhunter.business.NotificationManager;
import edu.usc.palhunter.business.UserManager;
import edu.usc.palhunter.db.Notification;
import edu.usc.palhunter.db.User;

public class Main {

  public static void main(String[] args) {
    // TODO Auto-generated method stub
    System.out.println("Hello Palhunter");
    // getDataTest();
    // setRidTest();
    // pushNotificationTest();
//    insertFriendsTest();
    getFriendsTest();
  }

  public static void getDataTest() {
    UserManager manager = new UserManager();
    User user = manager.getUser(1);
    System.out.println(user.toString());
  }

  public static void setRidTest() {
    String rid = "abcdef";
    int userId = 2;
    NotificationManager manager = new NotificationManager();
    manager.setRegisterId(userId, rid);
    manager.close();
  }

  public static void insertFriendsTest() {
    int uid1 = 1, uid2 = 10;
    UserManager manager = new UserManager();
    manager.addFriendship(uid1, uid2);
//    manager.removeFriendship(uid1, uid2);
    manager.close();
  }
  public static void getFriendsTest() {
    int userId = 1;
    UserManager manager = new UserManager();
    List<User> friends = manager.getFriends(userId);
    manager.close();
    for (User u : friends) {
      System.out.println(u.toString()); 
    }
  }

  public static void pushNotificationTest() {
    // String regId =
    // "APA91bGsRTPehOQM-oUOQFRpvljP_uY70j5SieGI9Rq2acD-LnkIz5FP8_XNqaXBL3YiJnSOi_yuDmcnlboytLuZDwBqnkhhj8xdsQqgx_mdqDTz68sWh1XBG2I1Ol7t4XAPDQsK5_hM2rvRe692cB8b220c0oY_Rg";
    List<Integer> userIds = new ArrayList<Integer>();
    userIds.add(1);
    // userIds.add(21);
    NotificationManager manager = new NotificationManager();
    String content = "A message from Palhunter server";
    manager.sendUserNotification(userIds, content, Notification.FRIEND_MESSAGE);
    manager.close();
  }

}

package com.varshad.friend.data;

import com.varshad.friend.model.User;

import java.util.List;
import java.util.Map;

public interface UserDAO {
    Map createUser(User user);
    Map getUser(String email);
    Map makeFriends(User user1, User user2);
    List checkFriends(User user1, User user2);
    List getFriends(User user);
    List getCommonFriends(User user1, User user2);
}

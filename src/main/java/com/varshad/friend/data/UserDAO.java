package com.varshad.friend.data;

import com.varshad.friend.model.User;
import com.varshad.friend.model.request.SubscribeBlockRequest;

import java.util.List;
import java.util.Map;

public interface UserDAO {
    Map createUser(User user);
    Map getUser(String email);
    Map makeFriends(User user1, User user2);
    Map checkFriends(User user1, User user2);
    List getFriends(User user);
    List getCommonFriends(User user1, User user2);
    Map subscribe(SubscribeBlockRequest subscribeRequest);
    Map block(SubscribeBlockRequest blockRequest);
    Map checkSubscribe(SubscribeBlockRequest subscribeRequest);
    Map checkBlock(SubscribeBlockRequest blockRequest);
    List getSubscribers(User user);
    List getBlockers(User user);
}

package com.varshad.friend.service;

import com.varshad.exceptions.InvalidParameterException;
import com.varshad.friend.data.UserDAO;
import com.varshad.friend.model.User;
import com.varshad.friend.model.request.FriendRequest;
import com.varshad.friend.model.request.SendUpdateRequest;
import com.varshad.friend.model.request.SubscribeBlockRequest;
import com.varshad.friend.model.response.FriendListResponse;
import com.varshad.friend.model.response.UpdatesReceiversResponse;
import com.varshad.friend.service.response.BlockResponse;
import com.varshad.friend.service.response.CreateUserResponse;
import com.varshad.friend.service.response.SubscriptionResponse;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.varshad.friend.service.response.CreateUserResponse.*;

public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;

    @Inject
    UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public CreateUserResponse createAndConnectUser(FriendRequest friendRequest) {
        Map map1 = userDAO.getUser(friendRequest.getFriends()[0].getEmail());
        Map map2 = userDAO.getUser(friendRequest.getFriends()[1].getEmail());
        if (map1 == null) {
            userDAO.createUser(friendRequest.getFriends()[0]);
        }
        if (map2 == null) {
            userDAO.createUser(friendRequest.getFriends()[1]);
        }
        Map friendlist = userDAO.checkFriends(friendRequest.getFriends()[0], friendRequest.getFriends()[1]);
        Map checkBlocked = userDAO.checkBlock(new SubscribeBlockRequest(friendRequest.getFriends()[0].getEmail(),
                friendRequest.getFriends()[1].getEmail()));
        if (checkBlocked.containsValue(true)) {
            return USER_BLOCKED;
        }
        if (friendlist.containsValue(false)) {
            userDAO.makeFriends(friendRequest.getFriends()[0], friendRequest.getFriends()[1]);
            return FRIEND_CONNECTION_SUCCESS;
        } else
            return FRIENDS_ALREADY;

    }

    @Override
    public FriendListResponse getFriends(User user) {
        List<Map> friends = userDAO.getFriends(user);
        FriendListResponse response = new FriendListResponse();
        ArrayList<String> users = new ArrayList<>();
        for (Map friend : friends) {
            users.add(userMapper(friend.get("user")).getEmail());
        }
        response.setFriends(users);
        response.setCount(users.size());
        return response;
    }

    @Override
    public FriendListResponse getCommonFriends(FriendRequest friendRequest) {
        List<Map> friends = userDAO.getCommonFriends(friendRequest.getFriends()[0], friendRequest.getFriends()[1]);
        FriendListResponse response = new FriendListResponse();
        ArrayList<String> users = new ArrayList<>();
        for (Map friend : friends) {
            users.add(userMapper(friend.get("user")).getEmail());
        }
        response.setFriends(users);
        response.setCount(users.size());
        return response;
    }

    @Override
    public SubscriptionResponse subscribe(SubscribeBlockRequest request) {
        Map map1 = userDAO.getUser(request.getRequestor());
        Map map2 = userDAO.getUser(request.getTarget());
        if (map1 == null || map2 == null) {
            return SubscriptionResponse.USER_NOT_AVAILABLE;
        }
        Map subscriptionCheck = userDAO.checkSubscribe(request);
        if (subscriptionCheck.containsValue(true)) {
            return SubscriptionResponse.ALREADY_SUBSCRIBED;
        } else {
            userDAO.subscribe(request);
            return SubscriptionResponse.SUBSCRIPTION_SUCCESS;
        }
    }

    @Override
    public BlockResponse block(SubscribeBlockRequest request) {
        Map map1 = userDAO.getUser(request.getRequestor());
        Map map2 = userDAO.getUser(request.getTarget());
        if (map1 == null || map2 == null)
            return BlockResponse.USER_NOT_AVAILABLE;

        Map blockCheck = userDAO.checkBlock(request);
        if (blockCheck.containsValue(false)) {
            userDAO.block(request);
            return BlockResponse.BLOCK_SUCCESS;
        } else
            return BlockResponse.ALREADY_BLOCKED;
    }

    @Override
    public UpdatesReceiversResponse getReceipients(SendUpdateRequest request) {
        List<Map> friends = userDAO.getFriends(request.getSender());
        List<Map> subscribers = userDAO.getSubscribers(request.getSender());
        List<Map> blockers = userDAO.getBlockers(request.getSender());
        List<Map> finalList = new ArrayList<>();
        if (friends != null)
            finalList.addAll(friends);
        if (subscribers != null)
            for (Map subscriber : subscribers) {
                if (!finalList.contains(subscriber))
                    finalList.add(subscriber);
            }
        Matcher m = Pattern.compile("[\\w.]+@[\\w.]+").matcher(request.getText());
        while (m.find()) {
            Map user = userDAO.getUser(m.group());
            if (user != null)
                if (!finalList.contains(user))
                    finalList.add(user);
        }
        if (blockers != null)
            for (Map blockedUser : blockers) {
                if (finalList.contains(blockedUser))
                    finalList.remove(blockedUser);
            }
        UpdatesReceiversResponse response = new UpdatesReceiversResponse();
        List<String> recipients = new ArrayList<>();
        for (Map user : finalList)
            recipients.add(userMapper(user.get("user")).getEmail());
        response.setReceipients(recipients);
        return response;
    }


    private User userMapper(Object string) {
        if (string.equals(null) || string == null)
            return null;
        String[] strings = string.toString().split("=");
        String email = strings[1].substring(0, strings[1].length() - 1);
        if (string == null || string == "null" || email == null || email == "null")
            return null;
        else
            try {
                return new User(email);
            } catch (InvalidParameterException e) {
                return null;
            }
    }
}

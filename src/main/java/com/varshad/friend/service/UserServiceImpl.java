package com.varshad.friend.service;

import com.varshad.exceptions.InvalidParameterException;
import com.varshad.friend.data.UserDAO;
import com.varshad.friend.model.User;
import com.varshad.friend.model.request.FriendRequest;
import com.varshad.friend.model.response.FriendListResponse;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserServiceImpl implements UserService {

   private final UserDAO userDAO;

    @Inject
    UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public CreateUserResponse createAndConnectUser(FriendRequest friendRequest) {
       try{
           Map map1 = userDAO.getUser(friendRequest.getFriends()[0].getEmail());
           Map map2 = userDAO.getUser(friendRequest.getFriends()[1].getEmail());
           if(map1==null){
               userDAO.createUser(friendRequest.getFriends()[0]);
           }
           if(map2 == null){
               userDAO.createUser(friendRequest.getFriends()[1]);
           }
           List friendlist = userDAO.checkFriends(friendRequest.getFriends()[0],friendRequest.getFriends()[1]);
           if(friendlist == null || friendlist.isEmpty()) {
               userDAO.makeFriends(friendRequest.getFriends()[0], friendRequest.getFriends()[1]);
           }
           return CreateUserResponse.FRIEND_CONNECTION_SUCCESS;
       } catch (Exception ex){
           System.out.println(ex);
           return  CreateUserResponse.CREATE_USER_FAILED;
       }

    }

    @Override
    public FriendListResponse getFriends(User user) {
        List<Map> friends = userDAO.getFriends(user);
        FriendListResponse response = new FriendListResponse();
        ArrayList<String> users = new ArrayList<>();
        for (Map friend: friends) {
            users.add(userMapper(friend.get("userFriends")).getEmail());
        }
        response.setFriends(users);
        response.setCount(users.size());
        return response;
    }

    @Override
    public FriendListResponse getCommonFriends(FriendRequest friendRequest) {
        List<Map> friends = userDAO.getCommonFriends(friendRequest.getFriends()[0],friendRequest.getFriends()[1]);
        FriendListResponse response = new FriendListResponse();
        ArrayList<String> users = new ArrayList<>();
        for (Map friend: friends) {
            users.add(userMapper(friend.get("usersFriends")).getEmail());
        }
        response.setFriends(users);
        response.setCount(users.size());
        return response;
    }

    private User userMapper(Object string){
        if(string.equals(null) || string == null){
            return null;
        }
        String[] strings = string.toString().split("=");
        String email = strings[1].substring(0,strings[1].length()-1);
        if(string == null || string=="null" || email == null || email == "null")
            return null;
        else {
            try {
                return new User(email);
            } catch (InvalidParameterException e) {
                return null;
            }
        }
    }
}

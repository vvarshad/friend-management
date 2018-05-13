package com.varshad.friend.service;

import com.varshad.friend.model.User;
import com.varshad.friend.model.request.FriendRequest;
import com.varshad.friend.model.response.FriendListResponse;

public interface UserService {
    CreateUserResponse createAndConnectUser(FriendRequest friendRequest);
    FriendListResponse getFriends(User user);
    FriendListResponse getCommonFriends(FriendRequest friendRequest);
}



package com.varshad.friend.service.users;

import com.varshad.friend.models.User;
import com.varshad.friend.models.request.FriendRequest;
import com.varshad.friend.models.response.FriendListResponse;

public interface UserService {
    CreateUserResponse createAndConnectUser(FriendRequest friendRequest);
    FriendListResponse getFriends(User user);
    FriendListResponse getCommonFriends(FriendRequest friendRequest);
}



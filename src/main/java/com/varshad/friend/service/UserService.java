package com.varshad.friend.service;

import com.varshad.friend.model.User;
import com.varshad.friend.model.request.FriendRequest;
import com.varshad.friend.model.request.SendUpdateRequest;
import com.varshad.friend.model.request.SubscribeBlockRequest;
import com.varshad.friend.model.response.FriendListResponse;
import com.varshad.friend.model.response.UpdatesReceiversResponse;
import com.varshad.friend.service.response.BlockResponse;
import com.varshad.friend.service.response.CreateUserResponse;
import com.varshad.friend.service.response.SubscriptionResponse;

public interface UserService {
    CreateUserResponse createAndConnectUser(FriendRequest friendRequest);
    FriendListResponse getFriends(User user);
    FriendListResponse getCommonFriends(FriendRequest friendRequest);
    SubscriptionResponse subscribe(SubscribeBlockRequest request);
    BlockResponse block(SubscribeBlockRequest request);
    UpdatesReceiversResponse getReceipients(SendUpdateRequest request);

}



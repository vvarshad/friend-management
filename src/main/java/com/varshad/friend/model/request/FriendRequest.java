package com.varshad.friend.models.request;

import com.varshad.friend.models.User;

import javax.validation.Valid;

public class FriendRequest {

    @Valid
    private User[] friends = new User[2];

    public User[] getFriends() {
        return this.friends;
    }

    public void setFriends(User[] friends) {
        this.friends = friends;
    }
}

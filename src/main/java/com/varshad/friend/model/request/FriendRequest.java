package com.varshad.friend.model.request;

import com.varshad.friend.model.User;

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

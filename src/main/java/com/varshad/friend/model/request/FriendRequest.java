package com.varshad.friend.model.request;

import com.varshad.exceptions.InvalidParameterException;
import com.varshad.friend.model.User;

import javax.validation.Valid;


public class FriendRequest {

    @Valid
    private User[] friends;

    public FriendRequest(){}

    public FriendRequest(User[] friends) throws InvalidParameterException {
        if(friends.length == 2){
            this.friends = friends;
        }
        else{
            throw new InvalidParameterException("invalid number of emails sent. Please send 2 email adresses only");
        }

    }

    public User[] getFriends() {
        return this.friends;
    }

    public void setFriends(User[] friends) throws InvalidParameterException {
        if(friends.length == 2){
            this.friends = friends;
        }
        else{
            throw new InvalidParameterException("invalid number of emails sent. Please send 2 email adresses only");
        }
    }
}

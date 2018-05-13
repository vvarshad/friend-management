package com.varshad.friend.models.request;

import com.varshad.friend.models.User;

public class SendUpdateRequest {
    private User sender;
    private String text;

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}

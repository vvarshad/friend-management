package com.varshad.friend.models.request;

import com.varshad.friend.models.User;

public class SubscribeBlockRequest {
    User requestor;
    User target;

    public User getRequestor() {
        return this.requestor;
    }

    public void setRequestor(User requestor) {
        this.requestor = requestor;
    }

    public User getTarget() {
        return this.target;
    }

    public void setTarget(User target) {
        this.target = target;
    }
}

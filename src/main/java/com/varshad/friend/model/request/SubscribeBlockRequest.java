package com.varshad.friend.model.request;

public class SubscribeBlockRequest {

    String requestor;
    String target;

    public SubscribeBlockRequest(){}

    public SubscribeBlockRequest(String requestor, String target) {
        this.requestor = requestor;
        this.target = target;
    }

    public String getRequestor() {
        return this.requestor;
    }

    public void setRequestor(String requestor) {
        this.requestor = requestor;
    }

    public String getTarget() {

        return this.target;
    }

    public void setTarget(String target) {
        this.target = target;
    }
}

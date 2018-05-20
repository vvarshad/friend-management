package com.varshad.friend.model.response;

import java.util.List;

public class FriendListResponse {

    private boolean success = true;
    private List<String> friends;
    private long count;

    public FriendListResponse(){

    }

    public FriendListResponse(List<String> friends, long count) {
        this.friends = friends;
        this.count = count;
    }

    public List<String> getFriends() {
        return friends;
    }

    public void setFriends(List<String> friends) {
        this.friends = friends;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    @Override
    public String toString() {
        return "FriendListResponse{" +
                "success=" + success +
                ", friends=" + friends +
                ", count=" + count +
                '}';
    }
}

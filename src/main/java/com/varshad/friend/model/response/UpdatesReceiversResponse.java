package com.varshad.friend.model.response;

import java.util.List;

public class UpdatesReceiversResponse {

    private boolean success = true;
    private List<String> receipients;


    public UpdatesReceiversResponse(){
    }

    public UpdatesReceiversResponse(List<String> receipients) {
        this.receipients = receipients;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<String> getReceipients() {
        return receipients;
    }

    public void setReceipients(List<String> receipients) {
        this.receipients = receipients;
    }
}

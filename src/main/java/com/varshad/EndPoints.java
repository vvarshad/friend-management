package com.varshad;

import com.varshad.friend.model.User;
import com.varshad.friend.model.request.FriendRequest;
import com.varshad.friend.service.CreateUserResponse;
import com.varshad.friend.service.UserService;
import org.jooby.Request;
import org.jooby.Result;
import org.jooby.Results;

import javax.inject.Singleton;

@Singleton
public class EndPoints {
    private static EndPoints instance;

    private EndPoints() {
    }

    public static EndPoints getInstance() {
        if (instance == null) {
            instance = new EndPoints();
        }
        return instance;
    }

    public Result HelloWorld(Request req){
        return Results.ok("You have reached Friend-Management API, Please access respective endpoints");
    }

    public Result connectFriends(Request req) {
        try {
            FriendRequest friendRequest = req.body().to(FriendRequest.class);
            UserService service = req.require(UserService.class);
            CreateUserResponse response = service.createAndConnectUser(friendRequest);
            switch (response){
                case CREATE_USER_FAILED: return Results.json(new ErrorResponse(400,"Creating  and Connecting Users failed"));
                case FRIEND_CONNECTION_SUCCESS: return Results.ok(new SuccessResponse());
                default: return Results.noContent().status(500);
            }
        }
        catch (Exception ex){
            return Results.json(new ErrorResponse(500, ex.getMessage())).status(500);
        }
    }

    public Result getFriends(Request req){
        try{
            User user = req.body().to(User.class);
            UserService service = req.require(UserService.class);
            return Results.ok(service.getFriends(user));
        }
        catch (Exception ex){
            return Results.json(new ErrorResponse(500, ex.getMessage())).status(500);
        }
    }

    public Result getCommonFriends(Request req){
        try{
            FriendRequest friendRequest  = req.body().to(FriendRequest.class);
            UserService service = req.require(UserService.class);
            return Results.ok(service.getCommonFriends(friendRequest));
        }
        catch (Exception ex){
            return Results.json(new ErrorResponse(500, ex.toString())).status(500);
        }
    }
}

class SuccessResponse{
    private Boolean success = true;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }
}

class ErrorResponse{
     private boolean success;
     private int status;
     private String message;

    ErrorResponse(int status, String message){
        success = false;
        this.status = status;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
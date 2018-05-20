package com.varshad;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidDefinitionException;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.varshad.friend.model.User;
import com.varshad.friend.model.request.FriendRequest;
import com.varshad.friend.model.request.SendUpdateRequest;
import com.varshad.friend.model.request.SubscribeBlockRequest;
import com.varshad.friend.service.UserService;
import com.varshad.friend.service.response.BlockResponse;
import com.varshad.friend.service.response.CreateUserResponse;
import com.varshad.friend.service.response.SubscriptionResponse;
import org.jooby.Request;
import org.jooby.Result;
import org.jooby.Results;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;

@Singleton
public class EndPoints {
    private static EndPoints instance;
    private Logger logger = LoggerFactory.getLogger(EndPoints.class);

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
                case FRIENDS_ALREADY: return Results.json(new ErrorResponse(400,"Users are already connected as friends"));
                case USER_BLOCKED: return Results.json(new ErrorResponse(400,"Friend request is blocked between the User"));
                case FRIEND_CONNECTION_SUCCESS: return Results.ok(new SuccessResponse());
            }
        }
        catch (Exception ex){
            return handleApiExceptions(ex);
        }
        return Results.noContent().status(500);
    }

    public Result getFriends(Request req){
        try{
            User user = req.body().to(User.class);
            UserService service = req.require(UserService.class);
            return Results.ok(service.getFriends(user));
        }
        catch (Exception ex){
            return handleApiExceptions(ex);
        }
    }

    public Result subscribe(Request req){
        try{
            SubscribeBlockRequest request  = req.body().to(SubscribeBlockRequest.class);
            UserService service = req.require(UserService.class);
            SubscriptionResponse response = service.subscribe(request);
            switch (response){
                case SUBSCRIPTION_SUCCESS: return Results.ok(new SuccessResponse());
                case ALREADY_SUBSCRIBED: return Results.json(new ErrorResponse(400,"requester has already subscribed to the target user")).status(400);
                case USER_NOT_AVAILABLE: return Results.json(new ErrorResponse(400,"User not available, Please create")).status(400);
            }
        }
        catch (Exception ex){
            return handleApiExceptions(ex);
        }
        return Results.noContent().status(500);
    }

    public Result block(Request req){
        try{
            SubscribeBlockRequest request  = req.body().to(SubscribeBlockRequest.class);
            UserService service = req.require(UserService.class);
            BlockResponse response = service.block(request);
            switch (response) {
                case BLOCK_SUCCESS: return Results.ok(new SuccessResponse());
                case ALREADY_BLOCKED: return Results.json(new ErrorResponse(400, "requester has already blocked to the target user")).status(400);
                case USER_NOT_AVAILABLE: return Results.json(new ErrorResponse(400, "User not available, Please create")).status(400);
            }
        }
        catch (Exception ex){
            return handleApiExceptions(ex);
        }
        return Results.noContent().status(500);
    }

    public Result updateReceipients(Request req){
        try{
            SendUpdateRequest request  = req.body().to(SendUpdateRequest.class);
            UserService service = req.require(UserService.class);
            return Results.ok(service.getReceipients(request));
        }
        catch (Exception ex){
            return handleApiExceptions(ex);
        }
    }

    public Result getCommonFriends(Request req){
        try{
            FriendRequest friendRequest  = req.body().to(FriendRequest.class);
            UserService service = req.require(UserService.class);
            return Results.ok(service.getCommonFriends(friendRequest));
        }
        catch (Exception ex){
            return handleApiExceptions(ex);
        }
    }

    private Result handleApiExceptions(Exception ex) {
        logger.error(ex.getLocalizedMessage());
        if(ex.getCause().getClass() == JsonMappingException.class ||
                ex.getCause().getClass() == InvalidDefinitionException.class){
            return Results.json(new ErrorResponse(400, ex.getCause().getCause().getMessage())).status(400);
        }else if(ex.getCause().getClass() == MismatchedInputException.class){
            return Results.json(new ErrorResponse(400, "Invalid body format for API endpoint. Please check the Json body of the request")).status(400);
        }else {
            ex.printStackTrace();
            return Results.json(new ErrorResponse(500, ex.getMessage())).status(500);
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

    @Override
    public String toString() {
        return "SuccessResponse{" +
                "success=" + success +
                '}';
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

    @Override
    public String toString() {
        return "ErrorResponse{" +
                "success=" + success +
                ", status=" + status +
                ", message='" + message + '\'' +
                '}';
    }
}
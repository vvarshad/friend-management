package com.varshad.friend.models;

import com.varshad.exceptions.InvalidParameterException;

import javax.validation.constraints.Pattern;

public class User {

    @Pattern(regexp = ".+@.+")
    String email;

    public User(){}

    public User(String email) throws InvalidParameterException{
        if(email.matches(".+@.+")) {
            this.email = email;
        }
        else throw new InvalidParameterException("User is not in valid format");
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

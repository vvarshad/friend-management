package com.varshad.friend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.varshad.exceptions.InvalidParameterException;

import javax.validation.constraints.Pattern;

@JsonIgnoreProperties(ignoreUnknown = true)
public class User {

    @Pattern(regexp = ".+@.+")
    String email;

    public User(){}

    public User(String email) throws InvalidParameterException{
        if(email.matches(".+@.+")) {
            this.email = email;
        }
        else throw new InvalidParameterException("User mail is not in valid format");
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

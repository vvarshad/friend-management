package com.varshad.friend.service;

import com.varshad.exceptions.InvalidParameterException;
import com.varshad.friend.data.UserDAO;
import org.junit.Test;

import static org.mockito.Mockito.mock;

public class UserServiceImplTest {

    private UserDAO userDAO = mock(UserDAO.class);

    private UserServiceImpl service= new UserServiceImpl(userDAO);

    @Test
    public void createAndConnectUser() throws InvalidParameterException {
       /* FriendRequest friendRequest = new FriendRequest(new User[]{new User("aa@aa.com"),new User("bb@bb.com")});
        when(userDAO)*/
    }

    @Test
    public void getFriends() {
    }

    @Test
    public void getCommonFriends() {
    }

    @Test
    public void subscribe() {
    }

    @Test
    public void block() {
    }

    @Test
    public void getReceipients() {
    }
}
package com.varshad;

import com.google.inject.Binder;
import com.typesafe.config.Config;
import com.varshad.friend.data.BoltCypherExecutor;
import com.varshad.friend.data.CypherExecutor;
import com.varshad.friend.data.UserDAO;
import com.varshad.friend.data.UserDAOImpl;
import com.varshad.friend.service.UserService;
import com.varshad.friend.service.UserServiceImpl;
import org.jooby.Env;
import org.jooby.Jooby;

;

public class MainModule  implements Jooby.Module{
    @Override
    public void configure(Env env, Config conf, Binder binder) throws Throwable {

        binder.bind(UserService.class).to(UserServiceImpl.class);

        binder.bind(CypherExecutor.class).to(BoltCypherExecutor.class);
        binder.bind(UserDAO.class).to(UserDAOImpl.class);
    }
}


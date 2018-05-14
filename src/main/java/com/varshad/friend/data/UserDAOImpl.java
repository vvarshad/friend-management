package com.varshad.friend.data;

import com.varshad.friend.model.User;
import com.varshad.friend.model.request.SubscribeBlockRequest;
import org.neo4j.helpers.collection.Iterators;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;

import static org.neo4j.helpers.collection.MapUtil.map;


public class UserDAOImpl implements UserDAO {

    private CypherExecutor cypherExecutor;

    @Inject
    UserDAOImpl(CypherExecutor cypherExecutor) {
        this.cypherExecutor = cypherExecutor;
    }

    @Override
    public Map createUser(User user) {
        return Iterators.singleOrNull(cypherExecutor.query("CREATE (n:User) " +
                "SET n.email = $email " +
                "RETURN n", map("email", user.getEmail())));
    }

    @Override
    public Map getUser(String email) {
        return Iterators.singleOrNull(cypherExecutor.query("MATCH (user:User) " +
                "Where user.email = $email " +
                "RETURN user", map("email", email)));
    }

    @Override
    public Map makeFriends(User user1, User user2) {
        return Iterators.singleOrNull(cypherExecutor.query(
                "MATCH (user1:User {email:$user1Email}) " +
                        "MATCH (user2:User {email:$user2Email}) " +
                        "CREATE (user1)-[friend:Friend]->(user2) ",
                map("user1Email", user1.getEmail(), "user2Email", user2.getEmail())));
    }

    @Override
    public Map checkFriends(User user1, User user2) {
        return Iterators.single(cypherExecutor.query(
                "RETURN EXISTS ((:User {email:$user1Email})-[:Friend]-(:User {email:$user2Email}))",
                map("user1Email", user1.getEmail(), "user2Email", user2.getEmail())));
    }

    @Override
    public List getFriends(User user) {
        return Iterators.asList(cypherExecutor.query(
                "MATCH (u:User {email:$email})-[:Friend]-(user) " +
                        "Return user",
                map("email", user.getEmail())));
    }

    @Override
    public List getCommonFriends(User user1, User user2) {
        return Iterators.asList(cypherExecutor.query(
                "MATCH (user1:User {email:$user1Email})-[:Friend]-(user) " +
                        "MATCH (user2:User {email:$user2Email})-[:Friend]-(user) " +
                        "Return user",
                map("user1Email", user1.getEmail(), "user2Email", user2.getEmail())));
    }

    @Override
    public Map subscribe(SubscribeBlockRequest subscribeRequest) {
        return Iterators.singleOrNull(cypherExecutor.query(
                "MATCH (user1:User {email:$user1Email}) " +
                        "MATCH (user2:User {email:$user2Email}) " +
                        "CREATE (user1)-[subscribe:Subscribes]->(user2) ",
                map("user1Email", subscribeRequest.getRequestor(), "user2Email", subscribeRequest.getTarget())));
    }

    @Override
    public Map block(SubscribeBlockRequest blockRequest) {
        return Iterators.singleOrNull(cypherExecutor.query(
                "MATCH (user1:User {email:$user1Email}) " +
                        "MATCH (user2:User {email:$user2Email}) " +
                        "CREATE(user1)-[r:Blocks]->(user2) ",
                        map("user1Email", blockRequest.getRequestor(), "user2Email", blockRequest.getTarget())));
    }

    @Override
    public Map checkSubscribe(SubscribeBlockRequest subscribeRequest) {
        return Iterators.single(cypherExecutor.query(
                "RETURN EXISTS ((:User {email:$user1Email})-[:Subscribes]->(:User {email:$user2Email})) ",
                map("user1Email", subscribeRequest.getRequestor(), "user2Email", subscribeRequest.getTarget())));
    }

    @Override
    public Map checkBlock(SubscribeBlockRequest subscribeRequest) {
        return Iterators.single(cypherExecutor.query(
                "RETURN EXISTS ((:User {email:$user1Email})-[:Blocks]-(:User {email:$user2Email})) ",
                map("user1Email", subscribeRequest.getRequestor(), "user2Email", subscribeRequest.getTarget())));
    }

    @Override
    public List getSubscribers(User user) {
        return Iterators.asList(cypherExecutor.query(
                "MATCH (u:User {email:$email})-[:Subscribes]-(user) " +
                        "Return user",
                map("email", user.getEmail())));
    }

    @Override
    public List getBlockers(User user) {
        return Iterators.asList(cypherExecutor.query(
                "MATCH (u:User {email:$email})-[:Blocks]-(user) " +
                        "Return user",
                map("email", user.getEmail())));
    }

}

package com.varshad.friend.data;

import com.varshad.friend.model.User;
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
    public List checkFriends(User user1, User user2) {
        return Iterators.asList(cypherExecutor.query(
                "MATCH (user1:User {email:$user1Email})-[friend:Friend]-(user2:User {email:$user2Email}) " +
                        "Return user1,friend,user2",
                map("user1Email", user1.getEmail(), "user2Email", user2.getEmail())));
    }

    @Override
    public List getFriends(User user) {
        return Iterators.asList(cypherExecutor.query(
                "MATCH (user:User {email:$email})-[:Friend]-(userFriends) " +
                        "Return userFriends",
                map("email", user.getEmail())));
    }

    @Override
    public List getCommonFriends(User user1, User user2) {
        return Iterators.asList(cypherExecutor.query(
                "MATCH (user1:User {email:$user1Email})-[:Friend]-(usersFriends) " +
                        "MATCH (user2:User {email:$user2Email})-[:Friend]-(usersFriends) " +
                        "Return usersFriends",
                map("user1Email", user1.getEmail(), "user2Email", user2.getEmail())));
    }
}

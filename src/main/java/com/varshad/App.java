package com.varshad;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.varshad.friend.data.Neo4jDriver;
import org.jooby.Jooby;
import org.jooby.apitool.ApiTool;
import org.jooby.json.Jackson;
import org.jooby.neo4j.Neo4j;
import org.neo4j.driver.v1.Driver;

/**
 * @author jooby generator
 */
public class App extends Jooby {
    private EndPoints endPoints;

    {
        use(new Jackson().doWith(mapper -> {
            mapper.setSerializationInclusion(Include.NON_ABSENT);
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        }));


        use(new Neo4j());

       use(new MainModule());

        onStart(registry -> {
            endPoints = EndPoints.getInstance();
            Neo4jDriver.getInstance().setDriver(require(Driver.class));
        });

        get("/", req -> endPoints.HelloWorld(req));
        post("/api/v1/users/connect", req -> endPoints.connectFriends(req));
        post("/api/v1/users/friends",req -> endPoints.getFriends(req));
        post("api/v1/users/friends/common", req -> endPoints.getCommonFriends(req));

        use(new ApiTool()
                .swagger("/swagger")
                .raml("/raml")
        );

    }

    public static void main(final String[] args) {
        run(App::new, args);
    }

}

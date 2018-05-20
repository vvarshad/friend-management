package com.varshad;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.varshad.friend.data.Neo4jDriver;
import org.jooby.Jooby;
import org.jooby.Results;
import org.jooby.json.Jackson;
import org.jooby.neo4j.Neo4j;
import org.neo4j.driver.v1.Driver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author jooby generator
 */
public class App extends Jooby {
    private EndPoints endPoints;
    private Logger logger = LoggerFactory.getLogger(App.class);

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
        before("/api/**", (req, rsp) -> {
            logger.trace("API request headers: " + req.headers());
            logger.trace("API request: " + req.ip() + " " + req.method() + " " + req.path() + " " + req.queryString());
        });
        after("/api/**", (req, rsp, result) -> {
            logger.trace("API response headers: "+ result.headers());
            logger.trace("API response: "+ result.status()+" "+ result.get());
            return result;
        });
        complete("/api/**", (req, rsp, cause) -> logger.warn("API error:" + cause));

        post("/api/v1/users/connect", req -> endPoints.connectFriends(req));
        post("/api/v1/users/friends", req -> endPoints.getFriends(req));
        post("api/v1/users/friends/common", req -> endPoints.getCommonFriends(req));
        post("api/v1/users/subscribe", req -> endPoints.subscribe(req));
        post("api/v1/users/block", req -> endPoints.block(req));
        post("api/v1/users/send-update", req -> endPoints.updateReceipients(req));

        installSwagger("/swagger");
    }

    public static void main(final String[] args) {
        run(App::new, args);
    }

    private String webJarVersion() {
        InputStream is = App.class.getResourceAsStream("/META-INF/maven/org.webjars/swagger-ui/pom.properties");
        Properties properties = new Properties();
        try {
            properties.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties.getProperty("version");
    }

    private void installSwagger(String path) {
        assets(path+"/swagger.json", "swagger.json");
        get(path,() ->  Results.redirect(path+"/ui/index.html?url="+path+"/swagger.json"));
        assets(path+"/ui/**", "/META-INF/resources/webjars/swagger-ui/"+webJarVersion()+"/{0}");
    }
}

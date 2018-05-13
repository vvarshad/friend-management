package com.varshad.friend.data;

import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.Value;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class BoltCypherExecutor implements CypherExecutor {
    private final Driver driver;

    public BoltCypherExecutor() {
        driver = Neo4jDriver.getInstance().getDriver();
    }

    @Override
    public Iterator<Map<String, Object>> query(String query, Map<String, Object> params) {
        try (Session session = driver.session()) {
            List<Map<String, Object>> list = session.run(query, params)
                    .list( r -> r.asMap(BoltCypherExecutor::convert));
            return list.iterator();
        }
    }

    static Object convert(Value value) {
        switch (value.type().name()) {
            case "PATH":
                return value.asList(BoltCypherExecutor::convert);
            case "NODE":
            case "RELATIONSHIP":
                return value.asMap();
        }
        return value.asObject();
    }
}

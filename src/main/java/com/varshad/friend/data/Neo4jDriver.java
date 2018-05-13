package com.varshad.friend.data;

import org.neo4j.driver.v1.Driver;

import javax.inject.Singleton;

@Singleton
public class Neo4jDriver {
    private static Neo4jDriver instance;
    private Driver driver;

    private Neo4jDriver(){}

    public static Neo4jDriver getInstance() {
        if (instance == null) {
            instance = new Neo4jDriver();
        }
        return instance;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }
}

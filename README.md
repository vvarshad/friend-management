## friend-management
Java API Application to manage friends. 
Framework: Jooby. 
Database: Neo4j.


To start the required services and run the application, execute the following commands in the terminal
```
docker-compose up -d
./gradlew joobyRun
```

`Note: Ports 8080, 7687, 7474 has to be free before running the above commands`

To run test cases
```
./gradlew check
```
To stop and delete the database 
```
docker-compose down -v
```

Api Documentation
```
Swagger Documentation link:
http://localhost:8080/swagger

```
To log in to the Neo4j DB Console
```
docker exec --interactive --tty <CONTAINER_ID> bin/cypher-shell
```

To log in to Neo4j Browser UI
```
http://localhost:7474/browser/
```

API Endpoints
```
friend-request    : POST http://localhost:8080/api/v1/users/connect
get-user-friends  : POST http://localhost:8080/api/v1/users/friends
get-mutual-friends: POST http://localhost:8080/api/v1/users/friends/common
subscribe-to-user : POST http://localhost:8080/api/v1/users/subscribe
block user        : POST http://localhost:8080/api/v1/users/block
get-receipients   : POST http://localhost:8080/api/v1/users/send-update 
```

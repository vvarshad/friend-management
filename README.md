# friend-management
Java API Application to manage friends. 
Framework: Jooby. 
Database: Neo4j.


To start the required services and run the application, execute the following commands in the terminal
```
docker-compose up -d
./gradlew joobyRun
```
To stop and delete the services
```
docker-compose down -v
```
To log in to the Neo4j DB Console
```
docker exec --interactive --tty <CONTAINER_ID> bin/cypher-shell
```

API Endpoints
```
friend-request    : POST http://localhost:8080/api/v1/users/connect
get-user-friends  : POST http://localhost:8080/api/v1/users/friends
get-mutual-friends: POST http://localhost:8080/api/v1/users/friends/common

```
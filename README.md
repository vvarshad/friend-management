# friend-management
Java API Application to manage friends. Framework Used: Jooby and Database: Neo4j.

To start the application, run the following command in the terminal.

```
gradle joobyRun
```
To start the database,
```
docker-compose up -d
```
To stop and delete the database
```
docker-compose down -v
```
To log in to the Neo4j DB Console

```
docker exec --interactive --tty <CONTAINER_ID> bin/cypher-shell
```
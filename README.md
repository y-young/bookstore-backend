# Bookstore Backend

An online bookstore powered by Spring Boot, from "Development of Internet Application" and "Architecture of Applications" courses.
Contents preceding tag [v1.0](../../releases/tag/v1.0) are from the former course and others are from the latter.

The frontend is [here](https://github.com/y-young/bookstore-frontend).

## Features & Highlights

-   Login and registration
-   Books management CRUD
-   Asynchronous order processing using JMS message queue
-   Sales and user purchase statistics
-   Fulltext search with Solr
-   Online chatroom using WebSocket
-   Unstructured data store with MongoDB\*
-   Related tags search with neo4j\*
-   Caching with Redis
-   Authentication using JWT with Spring Security filter
-   Authorization with Spring Security annotations
-   Request data validation using Spring Validation
-   Object Relation Mapping using Hibernate
-   Uniformed response format and common error handling
-   Swagger UI for RESTful API documentation and testing
-   Service discovery with Eureka\*
-   Microservices and Spring Cloud Gateway\*
-   Containerization and orchestration with Docker Compose

_Disclaimer: items marked with \* are assignment requirements that I personally do not consider necessary or as best practices given the application scenarios and the small size of this project, they're acceptable in a course project, but be sure to avoid over-engineering in real-life development._

## Run with Docker Compose

First you'll need to build Docker images for all components using `spring-boot:build-image`.
Executing `docker-compose up -d` will start all services and initialize an empty database with schemas.

To restore data from dumps or migrate from existing databases, follow the instructions below:

### Migrate MySQL Data

1.  Export existing database as an SQL file
2.  Import the dump: `docker exec -i CONTAINER_ID sh -c 'exec mysql -uroot -p"$MYSQL_ROOT_PASSWORD" -D bookstore' < bookstore.sql`

### Migrate MongoDB Data

1.  Export existing database as an archive: `mongodump -d bookstore --archive=bookstore.archive`
2.  Restore using the archive: `docker exec -i CONTAINER_ID sh -c 'exec mongorestore --archive' < bookstore.archive`

### Migrate neo4j Data

1.  Dump existing database: `./bin/neo4j-admin dump --database=neo4j --to=bookstore.dump`
2.  Stop neo4j Docker container if it's running
3.  Create a new container for restore operation, and mount the data volume of neo4j: `docker run -v bookstore-backend_neo4j-data:/data --name neo4j-restore -it neo4j:4.3
    /bin/bash`
4.  Copy the dump into the new container: `docker cp bookstore.dump CONTAINER_ID:/var/lib/neo4j`
5.  Load the dump into new database, execute in the new container: `./bin/neo4j-admin load --from=bookstore.dump --database=neo4j --force`

### Migrate Solr Data

1.  Enter `SOLR_DIRECTORY/server/solr/configsets/`
2.  Copy existing config set into container: `docker cp ./CONFIGSET_NAME/ CONTAINER_ID:/opt/solr-8.11.0/server/solr/configsets/bookstore`
3.  Create a Solr core using the config set: `docker exec -i --user=solr CONTAINER_ID bin/solr create_core -c books -d bookstore`
4.  Use API to trigger index initialization

## Run Manually

You'll need to manually install and configure all external programs like Solr and MongoDB according to `src/main/resources/application.yaml`.
Then build all projects using `mvn clean package` or your IDE to generate JAR files. Finally, start each component in the order defined in `docker-compose.yaml`.

## Plagiarism

There is NO TOLERANCE for plagiarism. All the code here is only for REFERENCE, you MUST write the code yourself, direct copying is strictly FORBIDDEN.

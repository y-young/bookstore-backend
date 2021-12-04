# Bookstore Backend

## Run with Docker Compose

Executing `docker-compose up -d` will start all services and initialize an empty database.

To restore data from dumps or migrate from existing databases, follow the instructions below:

### Migrate MySQL Data

1. Export existing database as an SQL file
2. Import the dump: `docker exec -i CONTAINER_ID sh -c 'exec mysql -uroot -p"$MYSQL_ROOT_PASSWORD" -D bookstore' < bookstore.sql`

### Migrate MongoDB Data

1. Export existing database as an archive: `mongodump -d bookstore --archive=bookstore.archive`
2. Restore using the archive: `docker exec -i CONTAINER_ID sh -c 'exec mongorestore --archive' < bookstore.archive`

### Migrate neo4j Data

1. Dump existing database: `./bin/neo4j-admin dump --database=neo4j --to=bookstore.dump`
2. Stop neo4j Docker container if it's running
3. Create a new container for restore operation, and mount the data volume of neo4j: `docker run -v bookstore-backend_neo4j-data:/data --name neo4j-restore -it neo4j:4.3
   /bin/bash`
4. Copy the dump into the new container: `docker cp bookstore.dump CONTAINER_ID:/var/lib/neo4j`
5. Load the dump into new database, execute in the new container: `./bin/neo4j-admin load --from=bookstore.dump --database=neo4j --force`

### Migrate Solr Data
1. Enter `SOLR_DIRECTORY/server/solr/configsets/`
2. Copy existing config set into container: `docker cp ./CONFIGSET_NAME/ CONTAINER_ID:/opt/solr-8.11.0/server/solr/configsets/bookstore`
3. Create a Solr core using the config set: `docker exec -i --user=solr CONTAINER_ID bin/solr create_core -c books -d bookstore`
4. Use API to trigger index initialization
## How to run the application:

### Prerequisites
- Make sure you have docker installed 
- Make sure docker-compose is installed if using an older version of the docker that doesn't package docker compose with it
- Run the following commands in the provided order:

```shell script
docker compose up -d
```

```shell script
./gradlew shadowJar
```

```shell script
java -jar ./build/libs/map-drawer.jar
```
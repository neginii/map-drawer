## How to run the application:

### Prerequisites
- Make sure you have JDK 11 installed 
- Make sure you have Docker installed 
- Make sure docker-compose is installed if using an older version of the Docker that doesn't package docker compose with it
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

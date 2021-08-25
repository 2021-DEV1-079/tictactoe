# Tictactoe

Game defaults:
- the port 8080 will be used
- the grid is 3x3

Player 1 is always "X".


## build & run
This projects uses gradle as its build tool.
There are multiple ways it can be run.

### directly via the gradlew wrapper
`./gradlew bootRun`  
Or if you want to specify a different port:   
`./gradlew bootRun --args='--server.port=<port number>'`


### Generate a jar & run it
Optionnaly you can generate the jar of the app and then run the jar.
1. `./gradlew bootJar`
2. `java -jar ./build/libs/TicTacToe-0.0.1-SNAPSHOT.jar` 
3. or with a different port :  `java -jar -Dserver.port=<port number> ./build/libs/TicTacToe-0.0.1-SNAPSHOT.jar`


## Endpoints
The app can be used through its different rest endpoints.

### 1. Create a new Game
```
curl --location --request POST 'localhost:8080/rest/v1/game/create'
```

This will return the :
- gameID 
- firstPlayerId 

### 2. Join the game as the second player
```
curl --location --request PUT 'localhost:8080/rest/v1/game/<game id>/join'
```

This way you will get the second player id.

### 3. Check game status
At any time you can check the status of a game.

``` 
curl --location --request GET 'localhost:8080/rest/v1/game/<game id>/state'
```

This will give you 
- the game status, one of : running, p1Won, p2Won, draw, awaitingSecondPlayer.
- a basic visual representation of the board
- the id of the next player to make a move

### 4. To play a move
Use your player id and specify a x and y to place your move.
```
curl --location --request PUT 'localhost:8080/rest/v1/game/<game id>/play' \
--header 'Content-Type: application/json' \
--data-raw '{
    "playerId" : "<player id>",
    "x" : 1,
    "y" : 1
}'
```

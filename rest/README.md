##Kodemon REST API documentation
#####Api root URL:
    /pa165/rest
####Contents:
* [User resource](#users)

###Users:
#####Root URL:
    /users
#####Model classes used:
* [UserDTO](https://github.com/mseleng/Kodemon/blob/master/api/src/main/java/com/kodemon/api/dto/UserDTO.java)
* [UserRegisterDTO](https://github.com/mseleng/Kodemon/blob/master/api/src/main/java/com/kodemon/api/dto/UserRegisterDTO.java)

#####Available endpoints:
* `GET /users`
    - **Description:** returns the list of all users in the system, represented as a `UserDTO` JSON object
    - **Example usage:** 
    ```bash
    curl http://localhost:8080/pa165/rest/users
    ```
    - **Example response:**
    ```json
    [{"id":1,"userName":"Brock","firstName":"Brock","lastName":"NotBlindman","dateOfBirth":"2007-10-10","badges":[],"pokemons":[{"id":1,"name":"GEODUDE","nickname":null,"level":15},{"id":2,"name":"OMANYTE","nickname":null,"level":16},{"id":3,"name":"RHYHORN","nickname":null,"level":14},{"id":4,"name":"GOLEM","nickname":null,"level":16},{"id":5,"name":"ONIX","nickname":null,"level":18}]},{"id":2,"userName":"MistygurlxD","firstName":"Misty","lastName":"Waterproof","dateOfBirth":"1995-08-07","badges":[],"pokemons":[{"id":6,"name":"GOLDEEN","nickname":null,"level":22},{"id":7,"name":"STARYU","nickname":null,"level":24},{"id":8,"name":"SEADRA","nickname":null,"level":21},{"id":9,"name":"WARTORTLE","nickname":null,"level":23},{"id":10,"name":"GOLDUCK","nickname":null,"level":25},{"id":11,"name":"GYARADOS","nickname":null,"level":25}]},{"id":3,"userName":"Headsh0tman","firstName":"Lucius","lastName":"Surge","dateOfBirth":"1989-12-20","badges":[],"pokemons":[{"id":12,"name":"MAGNEMITE","nickname":null,"level":28},{"id":13,"name":"ELECTRODE","nickname":null,"level":31},{"id":14,"name":"JOLTEON","nickname":null,"level":31},{"id":15,"name":"RAICHU","nickname":null,"level":32},{"id":16,"name":"ELECTABUZZ","nickname":null,"level":34}]},{"id":4,"userName":"Krowka227","firstName":"Erika","lastName":"Nadilowska","dateOfBirth":"1994-03-15","badges":[],"pokemons":[{"id":17,"name":"GLOOM","nickname":null,"level":36},{"id":18,"name":"TANGELA","nickname":null,"level":35},{"id":19,"name":"PARASECT","nickname":null,"level":37},{"id":20,"name":"VICTREEBEL","nickname":null,"level":37},{"id":21,"name":"VENUSAUR","nickname":null,"level":38}]},{"id":5,"userName":"MsPoIsON1997","firstName":"Janine","lastName":"Poisoulous","dateOfBirth":"1997-08-02","badges":[],"pokemons":[{"id":22,"name":"EKANS","nickname":null,"level":40},{"id":23,"name":"KOFFING","nickname":null,"level":43},{"id":24,"name":"NIDORAN_F","nickname":null,"level":43},{"id":25,"name":"GOLBAT","nickname":null,"level":42},{"id":26,"name":"MUK","nickname":null,"level":44},{"id":27,"name":"NIDOKING","nickname":null,"level":45}]},{"id":6,"userName":"Psyxox","firstName":"Sabrina","lastName":"McGonagall","dateOfBirth":"1991-02-10","badges":[],"pokemons":[{"id":28,"name":"DROWZEE","nickname":null,"level":49},{"id":29,"name":"ABRA","nickname":null,"level":50},{"id":30,"name":"HYPNO","nickname":null,"level":50},{"id":31,"name":"MRMIME","nickname":null,"level":52},{"id":32,"name":"ALAKAZAM","nickname":null,"level":55}]},{"id":7,"userName":"Quizman999","firstName":"Blaine","lastName":"Oldman","dateOfBirth":"1952-08-27","badges":[],"pokemons":[{"id":33,"name":"PONYTA","nickname":null,"level":54},{"id":34,"name":"CHARMELEON","nickname":null,"level":55},{"id":35,"name":"FLAREON","nickname":null,"level":58},{"id":36,"name":"ARCANINE","nickname":null,"level":56},{"id":37,"name":"MAGMAR","nickname":null,"level":57},{"id":38,"name":"CHARIZARD","nickname":null,"level":59}]},{"id":8,"userName":"BadGuy3","firstName":"Giovanni","lastName":"Margherita","dateOfBirth":"1988-11-05","badges":[],"pokemons":[{"id":39,"name":"SANDSLASH","nickname":null,"level":62},{"id":40,"name":"CUBONE","nickname":null,"level":62},{"id":41,"name":"MAROWAK","nickname":null,"level":64},{"id":42,"name":"GRAVELER","nickname":null,"level":63},{"id":43,"name":"DUGTRIO","nickname":null,"level":66},{"id":44,"name":"RHYDON","nickname":null,"level":70}]},{"id":9,"userName":"Ash123","firstName":"Ash","lastName":"Ketchum","dateOfBirth":"1995-01-03","badges":[],"pokemons":[{"id":45,"name":"PIKACHU","nickname":null,"level":1}]}]
    ```
* `GET /users/{id}`
    - **Description:** returns the detail of the user with the given id
    - **Path parameters:**
        + `id` - id of the user
    - **Example usage:**
    ```bash
    curl http://localhost:8080/pa165/rest/users/24
    ```
    - **Example response:**
    ```json
    {"id":1,"userName":"Brock","firstName":"Misty","lastName":"NotBlindman","dateOfBirth":"2007-10-10","badges":[],"pokemons":[{"id":1,"name":"GEODUDE","nickname":null,"level":15},{"id":2,"name":"OMANYTE","nickname":null,"level":16},{"id":3,"name":"RHYHORN","nickname":null,"level":14},{"id":4,"name":"GOLEM","nickname":null,"level":16},{"id":5,"name":"ONIX","nickname":null,"level":18}]}
    ```
    - **Response codes:**
        + 200 - if the resource was found
        + 404 - if the resource was not found

* `GET /users?userName={userName}`
    - **Description:** returns the list of users with username similar to the given one
    - **Query parameters:**
        + `userName` - username to look for
    - **Example usage:**
    ```bash
    curl http://localhost:8080/pa165/rest/users\?userName\=Brock
    ```
    - **Example response:**
    ```bash
    [{"id":1,"userName":"Brock","firstName":"Brock","lastName":"NotBlindman","dateOfBirth":"2007-10-10","badges":[],"pokemons":[{"id":1,"name":"GEODUDE","nickname":null,"level":15},{"id":2,"name":"OMANYTE","nickname":null,"level":16},{"id":3,"name":"RHYHORN","nickname":null,"level":14},{"id":4,"name":"GOLEM","nickname":null,"level":16},{"id":5,"name":"ONIX","nickname":null,"level":18}]}]
    ```
    
* `POST /users`
    - **Description:** creates a new user in the database (equivalent to the registration in UI)
    - **Request body:**
        + `userName`: `String` (required)
        + `firstName`: `String` (required)
        + `lastName`: `String` (required)
        + `dateOfBirth`: `Date` (required)
        + `pokemon`: `PokemonName` enum; One of `{SQUIRTLE, BULBASAUR, CHARMANDER}` (required)
        + `password`: `String` (required)
    - **Example usage:**
    ```bash
    curl -H "Content-Type: application/json" -X POST -d '{"userName":"PikaAsh","firstName":"Ash","lastName":"Ketchum","dateOfBirth":"2016-12-3","password": "asdfghjkl","pokemon": "BULBASAUR"}' http://localhost:8080/pa165/rest/users
    ```
    - **Response codes:**
        + 201 - if an entity was successfully created (also returns the `URI` of the newly created resource)
        + 400 - if the input data was incorrect
        + 409 - if a problem occurs while performing db operation (typically constraint violation)
            
* `PUT /users/{id}` **UNSTABLE AND MAY CHANGE**
    - **Description:** updates the user with the given id
    - **Path parameters:**
        + `id` - id of the user
    - **Request body:**
        + `firstName`: `String` (optional)
        + `lastName`: `String` (optional)
        + `dateOfBirth`: `Date` (optional)
    - **Example usage:**
    ```bash
    curl -H "Content-Type: application/json" -X PUT -d '{"firstName":"Misty"}' http://localhost:8080/pa165/rest/users/1
    ```
    - **Response codes:**
        + 201
        + 409 - if a problem occurs while performing db operation (typically constraint violation)

CAG Racing Management System
==========================
The ultimate racing management system.

Spec
----
https://docs.google.com/document/d/1U0zHYvEPcVbiDV0DH9tzJArNnOec-c7YbB-wD7Sztss/edit?usp=sharing

Mongo DB
--------
Install MongoDB: https://docs.mongodb.org/manual/installation/

MongoDB GUI, 3T MongoChef: http://3t.io/mongochef/download/

Start mongod: 

    $ mongod --dbpath <path to DB>

Tools
-----
REST Test tool:<br>
Postman: https://chrome.google.com/webstore/detail/postman/fhbjgbiflinjbdggehcddcbncdddomop?hl=en

Build from root pom.xml:
------------------------
Without integration-tests (doesn't require mongod installed locally):

```sh
mvn package
```

With integration-tests (requires local mongod running on port 27017):

```sh
mvn install
```

Build docker-images

```sh
mvn clean install -Pdocker
```

Start all docker-images together with docker-compose

```sh
docker-compose up
```


Portar
------
* current-race: 10080
* leaderboard: 10180
* user-manager: 10280
* race-administrator: 10380
* client-api: 10580

README

# Main Packages

- com.github.mattboschetti.kingdom.http
- com.github.mattboschetti.kingdom.scores


## http

Inside this package there are the classes responsible for dealing with the requests that the app will receive.
The application is build around a more general request handler, that route the requests to another handlers.

Router class is the core routing system. You use it to register the URL paths to specific handlers and also you
can register a handler to deal with not mapped paths. Currently the path you need to register with Router, is a
java regular expression. But the Router module is located outside the base request handler in order to be easily
extendible.

QueryStringExtractor has the logic to break strings down from &key=value&key=value format into a Map<String,String>.

This package is further divided into another 4 pacakges.

### http.filter

Classes that extends the httpserver.Filter, currently there is only one filter in the system, QueryStringFilter. 
Which is responsible for breaking the query string into proper parameters and make them available inside the 
HttpExchange attributes, under the name "queryParams" as a Map<String,String> type.

### http.handler

Base handler classes for the system. Currently we have 3 classes in it. 

CustomRequestHandler is just an utility class meant to provide some auxiliary methods. It could have been a kind
of mixin. But we are just currently extending it on other business specific handlers

NotFoundHandler will handle any URL that it is not mapped to a handler.

RequestHandler deals with all requests to the app. It will route the request to the appropriate business Handler
specific handler.


## scores

This package contains all business logic related classes. It contains three child packages, handlers, which contain
all business specific request handlers. Model, which contain all business specific classes and session

### scores.model

The model package contain all business logic classes. 

Game class is a singleton class, which is responsible for creating and retreiving level information. It is backed
by a ConcurrentHashMap for maintainging the Levels according to their id. New levels are created on demand.

Level class contains all the logic related to inserting and retrieving user scores, validating the input according
to the determined highscore rules and ordering the output. It is backed by a synchronized ArrayList to maintain
the 15 top scores for a specific level. This class is created on demand by the Game class whenver the user sends
user scores or the user retrieves the highscores for a level that doesn't exist.

The strategy behind the Level class is that everytime we inser a new score data for a specific level, we check
the score list size, we make sure that it has at maximum 15 elements and we leave it already sorted for the 
retrieve high scores operation.

Score and User classes are single pojos, but they differ in the fact that they are immutable. So we can try to
avoid maintaining state in just a few classes under this concurrent environment.

## session

The session package is basically responsible for providing the user login and the mapping between the userid and 
the session key.

Expiration and Key classes are immutable classes that only hold the userid, session key and the time that the
session was created.

Session class is a singleton and it is backed by two ConcurrentHashMaps, that will keep track of sessions that will
expire, and the mapping between users and keys. The expired sessions are never removed until you request an expired
one. But we could have a scheduled thread that could do this cleanup each 5 or 10 minutes.

## util

There is only one class in this package. A singleton class that is reposnible for generating random alphanumeric
data for the session key. Currently we have a singleton, which can cause performance issues if shared by too many
threads. As per java documentation suggestion, we could move to a thread local random.

# Main.java

App entry point, responsible for setting up the HttpServer (its ports, handlers and routes) as well as the number
of threads supporting the server and the socket where clients will connect.

Without much fine tunning the app is able to handle more or less 150 concurrent threads requesting data. Changing
default socket ha
ndlers at HttpServer.create, and playing with the thread pool size, we might be able to get more
concurrent threads flowing.

As far as it was tracked, the heap size is very stable, even with peak loads. The permgen space suffer a little 
increase over time, although I wasn't able to stress it out.

# test directory

I've used junit for unit testing of more important classes. Also there is a very simple jmx file for JMetter in
order to support a very simple load test.


Matheus Boschetti - 28/Oct/2014

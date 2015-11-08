+-------------------------------------+
| TABLE OF CONTENTS                   |
+-------------------------------------+

- TEST & BUILD THE APPLICATION ........
- EXECUTE THE JAR IN THE COMMAND LINE .
- ON THE DESIGN CHOICES ...............
- REFERENCES ..........................

+-------------------------------------+
| TEST & BUILD THE APPLICATION        |
+-------------------------------------+

$ mvn clean verify package

Notes
-----

- Java 8 is required due to the use of method references.
- Unit tests might take a few seconds or so to complete, as one hundred increments are performed by
  a thread pool of 25 clients against the server to check against concurrency issues.
- After the project is built, JavaDocs will be available in the following location:
  ./target/apidocs/index.html

+-------------------------------------+
| EXECUTE THE JAR IN THE COMMAND LINE |
+-------------------------------------+

Usage
-----

Start as a Server:
$ java -jar ./target/counter-1.0-SNAPSHOT-jar-with-dependencies.jar {--mode=S}
                                                                    [--port=<port number>]

Run as a Client:
$ java -jar ./target/counter-1.0-SNAPSHOT-jar-with-dependencies.jar {--mode=C --operation=<I(ncrement),
                                                                                           D(ecrement),
                                                                                           R(eset) or
                                                                                           E(valuate)>}
                                                                    [--host=<host name>]
                                                                    [--port=<port number>]

Configuration
-------------

Note that port and host are optional. Default values for these parameters are configured in the following
properties file:
./src/main/resources/spring/configuration.properties

Examples
--------

Start Server using the default port:
$ java -jar ./target/counter-1.0-SNAPSHOT-jar-with-dependencies.jar --mode=S

Run Client using the default port and host to evaluate counter:
$ java -jar ./target/counter-1.0-SNAPSHOT-jar-with-dependencies.jar --mode=C --operation=E

+-------------------------------------+
| ON THE DESIGN CHOICES               |
+-------------------------------------+

I initially considered implementing a  socket server from scratch using the I/O API introduced in Java 7,
which also includes Non-Blocking I/O (NIO.2). Spring  Integration provides  UDP and TCP  support though,
therefore, as I feel like reinventing  the wheel is always a bad idea, this application uses Spring.

Spring Integration IP [1] provides  out-of-the-box implementations for both TCP server and client through
bean configuration. The application uses the following application context files:

- Server: ./src/main/resources/spring/server/applicationContext.xml
- Client: ./src/main/resources/spring/client/applicationContext.xml

For the server  side, Spring provides a  connection factory,  channel  and serializer/deserializer  beans
as well. For the client side, Spring additionally provides a gateway bean to route messages.

Spring  Integration IP  supports NIO  by simply adding  the  flag "using-nio" to  both  server and  client
connection factory configuration in the  application  context,  so we have the choice of using this option
without much hassle. Using NIO or not is open to discussion though [3].

The "protocol" defined for exchanging messages is quite simple:

- "I", as a string, is sent to increment the counter.
- "D", to decrement.
- "E, to evaluate.
- "R, to reset.

For any of these messages, the up-to-date  value  of the counter is  replied back as  a string, e.g., "123".
I decided to keep the messages short to minimize the use of the network.

For solving  concurrency issues, I  decided  to  take  advantage the  Java  Concurrency  API, available from
Java 5. For simplicity, I have used an atomic-type to hold the value of the counter (AtomicInteger).

Another option is the  use of locks.  In case  the  number of reads (counter evaluates)  is greater than the
number of writes (counter increments, decrements and resets), a good option would be using ReadWriteLock.
Since CounterService  encapsulates the  task of  updating the count, the class  can be  easily refactored to
use any locks available in the Java Concurrency API [2].

Given  that there  is no hint  of such  scenario  in  the  requirements  document, I  opted for the solution
that allows the cleanest code ("Premature optimization is the root of all evil" -- Donald Knuth).

+-------------------------------------+
| REFERENCES                          |
+-------------------------------------+

[1] Spring Integration, UDP and TCP support:
http://docs.spring.io/spring-integration/reference/html/ip.html

[2] Java Concurrency API:
https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/locks/package-summary.html

[3] NIO vs. IO:
http://docs.spring.io/spring-integration/reference/html/ip.html#note_nio

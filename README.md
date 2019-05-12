# Getting Started

### Reference Documentation
This maven project is a technical test. It consists in a very simple booking system as a REST Microservice in spring boot.

### Local Installation
To install the project on your IDE, clone the repository and import the existing "booking-system" project as Maven project into the IDE. Then build the project.

### Run
There are several options to run the application:

1. Using your IDE.
Once the project is imported and built, you can run it directly from your IDE to do so, execute the main class of the project "BookingSystemApplication.java" as Java main application (as this is a spring boot application). 
Note: Java 8 (or higher) and embedded Maven is needed in the IDE. 

2. Using console and Maven command line.
Go to the project directory (cd ~\booking-system). Execute "mvn spring-boot:run".
Note: Java 8 (or higher) and Maven tool must be present in the system environment path.

3. Generate artifacts with Maven and execute with "java -jar".
In the project directory execute "mvn package", this action will generate the main artifact in "~\booking-system\target".
Go to that directory and execute "java -jar bookingsystem-0.0.1-SNAPSHOT.jar"
Note: Java 8 (or higher) and Maven tool must be present in the system environment path.

With either option the application will start automatically in port 8080. Use the end-points below to interact with the application.
To Run Junit tests it's needed to use the JDK 8 (or higher) test cases do not work with JRE.


### End points
When the application starts it creates booking example data. You can manage them with the following end points that the application provides.

1. GET - /bookings
It returns all available bookings (it means only the ones with available status). When applications starts all bookings are in this status.
Example request: "http://localhost:8080/bookings" - Method GET

2. POST - /bookings/{Booking ID}/block
It blocks the provided booking id during a predefined time (configurable in application properties). If the booking is not confirmed afterwards then it will be available again.
Only available bookings can be blocked.
Example request: "http://localhost:8080/bookings/1/block" - Method POST

3. POST - /bookings/{Booking ID}/confirm
It confirms the provided booking id. Only already blocked bookings can be confirmed.
Example request: "http://localhost:8080/bookings/1/confirm" - Method POST

4. POST - /bookings/{Booking ID}/cancel
It cancel the provided booking id. Only not available bookings can be canceled.
Example request: "http://localhost:8080/bookings/1/cancel" - Method POST

If wish, it's possible query the data of the database. H2 console is enabled, you can go to "http://localhost:8080/h2-console/login.jsp" for acceding.
Configuration and credentials are:

* Driver Class: org.h2.Driver
* JDBC URL: jdbc:h2:mem:bookingsystem
* User Name: sa
* Password: <leave empty>


### Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/bookmarks/)
* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)
* [H2 database](https://www.h2database.com/html/main.html)
* [Flyway](https://flywaydb.org/)
* [Lombock](https://projectlombok.org/)
* [JUnit](https://junit.org/junit4/)
* [Maven](https://maven.apache.org/)

### Other main features good to have for a better performance in a real scenarios but no added to the test
* [Caching Data with Spring](https://spring.io/guides/gs/caching/)

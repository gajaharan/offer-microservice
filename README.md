# WP Offer Service

## Product Details:

A simple RESTful software service that will
* That will allow a merchant to create a new simple offer.
* Once Offer has been created, may be queried.
* After the period of time defined on the offer it should expire.
* Further requests to query the offer should reflect that somehow.
* Before an offer has expired users may cancel it.

Offer will have
* friendly descriptions
* price all my offers
* up front in a defined currency
* An offer is time-bounded
* and should expire automatically
* Offers may also be explicitly cancelled before they expire

## Technologies
* Maven 3+
* Java 8
* Spring Boot 2.1.7
* JPA with H2 database
* Testing - Junit, Mockito, Rest Assured.

## Build and Run

### How to build:
`mvn clean install`


### How to run
* Run as a Spring Boot local application
* `mvn clean install spring-boot:run -Dspring.profiles.active=local`

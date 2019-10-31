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

## Overview

Contract definition can be found [here](https://raw.githubusercontent.com/gajaharan/offer-microservice/master/src/main/resources/swagger.yaml)

### Summary 
| Endpoint             | method   | Status                            |
|:---------------------|:---------|:---------------------------------|
| `/offers`            |   POST   | Response code 201 and Location header, 400, 500 |
| `/offers/{id}`       |   GET    | Response code 200, 404, 500 |
| `/offers/{id}/cancel`|   DELETE | Response code 200, 404, 409, 500 |

### Example POST request
```
{
    "description": "test 3",
    "amount": 2.99,
    "currency": "GBP",
    "startDate": "2019-10-28 23:45",
    "endDate": "2019-10-29 23:45"
}
```

### Example GET response
```
{
    "id": 3,
    "description": "test 3",
    "amount": 2.99,
    "currency": "GBP",
    "startDate": "2019-10-28 23:45",
    "endDate": "2019-10-29 23:45",
    "status": "EXPIRED"
}
```

### Assumptions
* Spent time designing the endpoint using Swaggerhub and created the swagger yaml file (in main resource folder).
I believe
* Offer model is used is used as POST payload, GET response and entity. Ideally would of seperated this as three
seperate models.
* Kept the controller as simple as possible with three endpoints (create, retrieve by id, cancel by id)
* Most of the business logic in the Offer Service
* If time permitted would like to add validation to the start and end date, so the user will get a 400 response about
correct date format, if the start date is before end date, etc. Assuming the front end will check this.
* The offer expiry is checked when the offer is retrieved.
* Offer statuses are saved into the database
mutation-web-service
====================

Prerequisites:
- MySql
- Maven

MySql setup
* Need to have the MySql Server running
* Need to create a database called 'checkout'
    CREATE DATABASE checkout;
* Need to add a user 'cashier' with password 'basket'
    CREATE USER 'cashier'@'localhost' IDENTIFIED BY 'basket';
* The 'cashier' user needs permissions to the 'checkout' database
    GRANT ALL PRIVILEGES ON checkout . * TO 'cashier'@'localhost';

You should be able to run the unit tests:
    mvn clean test

And the integration tests:
    mvn clean verify -Pintegration-tests

To run the server (listening on Port 9988):
    mvn exec:java -Dexec.mainClass="checkout.CheckoutServer"

Current REST operations supported are:

* Registering a team (replace "my_team_name" with your chosen name)
    PUT /Checkout/Team
    JSON Payload : {"name":"my_team_name"}
    Response 201 - success (with actual name registered returned in JSON - it may have been trimmed)
    Response 400 - failure (response shows what was wrong with submission)

* Getting requirements for current round
    GET /Checkout/Requirements/my_team_name
    Response 200 - success (returns requirements in JSON as plain text)
    Response 400 - failure (response shows what was wrong with request)

* Getting a batch of baskets for pricing
    GET /Checkout/Batch/my_team_name
    Response 200 - success (returns batch in JSON)
    - the batch is made up of Baskets each of which has a unique ID
      and zero or more items
    Response 400 - failure (response shows what was wrong with request)

* Getting the current price list
    Get /Checkout/PriceList/my_team_name
    Response 200 - success (returns price list in JSON)
    Response 400 - failure (response shows what was wrong with request)

* Submitting prices for the current batch
    PUT /Checkout/Batch/my_team_name
    JSON Payload : {"batch":{"baskets":{"1":{"dollars":0,"cents":25},"2":{"dollars":0,"cents":25}}}}
    - totals for all baskets have to be submitted at the same time
    Response 201 - success (team now moves on to next round)
    Response 400 - failure (response shows what was wrong with submission)

* Getting score for a team
    GET /Checkout/Score/my_team_name
    Response 200 - success (score in JSON payload)
    Response 400 - failure (response shows what was wrong with request)


# Mutation workshop java client

Starter code to call checkout service

To run mutation tests

```
mvn test
```

Output will be written to target/pit-reports.

(note mutation testing is more typically bound to verify)

To run the app

```
mvn exec:java -Dexec.mainClass="uk.co.claysnow.checkout.app.App" -Dexec.args="requirements 172.16.66.1 9988 teamName"
```



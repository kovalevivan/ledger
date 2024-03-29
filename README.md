# Ledger service

Test implementation of API for transfering money between accounts

## How to start

1. Build an app
```
mvn clean install
```
2. Start it
```
java -jar target/ledger-1.0-SNAPSHOT.jar
```

## Test data

This API don't have methods to manage account data so, I added initial test accounts.

1. account: dd5e091d-fc50-4d39-a958-8892873a6829 balance: 100
2. account: 902cc3fe-bcd2-4072-a069-d3688235f8bc balance: 100


## Tech stack

1. Java 17
2. Jersey 3.X
3. Jetty 11.X
4. Spock/Groovy/Junit 5

## API example

```
POST /api/transfer

{
    "accountFrom": "dd5e091d-fc50-4d39-a958-8892873a6829",
    "accountTo": "902cc3fe-bcd2-4072-a069-d3688235f8bc",
    "amount": 100
}
```

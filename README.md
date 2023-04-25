# Drone Delivery Service

This project is a REST API for a drone delivery service that allows clients to communicate with the drones and dispatch medication items. The API is built using Spring Boot and stores data in an H2 database.

## Installation and Usage
To build and run the project, you will need to have Java and Maven installed on your system.

1. Clone the repository to your local machine.

2. Open a terminal in the project directory.

3. Run the following command to build the project:

``` Shell
mvn clean package
```
4. Run the following command to start the server:

``` Bash
java -jar target/drone-delivery-service-1.0.0.jar
```
This will start the server on port 8080.

5. You can test the API endpoints using a tool like Postman or cURL. Here are some examples:

Register a drone:

``` JSON
POST http://localhost:8080/api/drones

{
  "serialNumber": "ABC123",
  "model": "Heavyweight",
  "weightLimit": 500,
  "batteryCapacity": 90,
  "state": "IDLE"
}
```
Load medication items onto a drone:

``` Bash
POST http://localhost:8080/api/drones/1/medications/load

{
  "name": "Aspirin",
  "weight": 10,
  "code": "ASPIRIN_001",
  "image": "base64-encoded-image-data"
}
```

Check loaded medication items for a drone:

``` Bash
GET http://localhost:8080/api/drones/1/medications
```
Check available drones for loading:

``` Bash
GET http://localhost:8080/api/drones/available
```
Check drone battery level:

``` Bash
GET http://localhost:8080/api/drones/1/battery
````
# Database
The project uses an H2 in-memory database to store data. The database schema is created automatically when the application starts up, using the schema.sql and data.sql files in the src/main/resources directory.

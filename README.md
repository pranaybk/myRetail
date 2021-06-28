# myRetail

## Getting Started

### Prerequisites
in order to run myReatil API, you'll need to run MongoDB local instance. Please follow below documentation.
https://docs.mongodb.com/manual/tutorial/install-mongodb-on-os-x/

### Technologies
JDK 1.8

Mango DB 4.4.5

SpringBoot 2.5.1

Maven

### Database Details

database=Products

colleciton=PriceInfo

mongodb host=localhost

mongodb port=27017

### Build, Test and Run Application 

mvn clean install  -Dspring.profiles.active=stage

java -jar -Dspring.profiles.active=stage  target/myRetail-0.0.1-SNAPSHOT.jar

### Testing API endpoint

### Get 

Request URL:  localhost:8081/products/13264003

Response: 
{
    "id": 13264003,
    "name": "Jif Natural Creamy Peanut Butter - 40oz",
    "current_price": {
        "value": 38.49,
        "currency_code": "USD"
    }
}

### Put

Request URL:  localhost:8081/products/13264003

Request Body: 
{
    "id": 13264003,
    "name": "Kraft Macaroni &#38; Cheese Dinner Original - 7.25oz",
    "current_price": {
        "value": 38.49,
        "currency_code": "USD"
    }
}
Response: 200 Sucess

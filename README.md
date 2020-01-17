# Authentication using JWT in Spring Boot
JWT based Authentication for REST Api.

## Getting Started

### Pre-Requisities
* Basic understanding of Spring Framework which includes Spring Boot, Spring Security and REST Api
* Understanding of JSON.

### Technologies
* JDK 1.8 and above
* Spring Boot 2.1.3-RELEASE
* Maven 3+
* Swagger 2
* POSTMan REST Client

## Running Spring Boot Application
Spring Boot Main Class, Just run below class like any another Java Code
* com.example.jwt.app.JwtApplication

### Credentials
* {	"username": "foo", "password": "foo" }

### Using POSTMan
* http://localhost:8080/authenticate
* You would receive JWT Token which will be used in Authorization Bearer Header for all other API

### Using Swagger UI
* http://localhost:8080/swagger-ui.html
* Use Controller Post Method: jwt-auth-controller
* You would receive JWT Token which will be used in Authorization Bearer Header for all other API

### Sample Output of JWT response from POST Method
* Request http://localhost:8080/authenticate
* { "jwt": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJmb28iLCJleHAiOjE1NzkyNjgwMDMsImlhdCI6MTU3OTI1MDAwM30.ijaVxlEHGqiSoUCnvFR6_GkBr_5mpoFMrVz_YeXnhzo" }

## Using CURL
* $ curl -X POST "http://localhost:8080/authenticate" -H "accept: application/json" -H "Content-Type: application/json" -d "{ \"password\": \"foo\", \"username\": \"foo\"}"

### Get the JWT and use it below

* $ curl -X GET "http://localhost:8080/jwt/welcome" -H "accept: application/xml" -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJmb28iLCJleHAiOjE1NzkyNjgwMDMsImlhdCI6MTU3OTI1MDAwM30.ijaVxlEHGqiSoUCnvFR6_GkBr_5mpoFMrVz_YeXnhzo"

## Authors
* NEWFOUND SYSTEMS http://www.newfound-systems.com

## License
* This project is licensed under the Open Free for all License.

## Acknowledgments
* Many Inspirations from our big Gurus out in Stack OverFlow. Thanks

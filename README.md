**CASE STUDY : myRetail RESTful service**

myRetail is a rapidly growing company with HQ in Richmond, VA and over 200 stores across the east coast. myRetail wants to make its internal data available to any number of client devices, from myRetail.com to native mobile apps. 
The goal for this exercise is to create an end-to-end Proof-of-Concept for a products API, which will aggregate product data from multiple sources and return it as JSON to the caller. 
Your goal is to create a RESTful service that can retrieve product and price details by ID. The URL structure is up to you to define, but try to follow some sort of logical convention.
Build an application that performs the following actions: 
•	Responds to an HTTP GET request at /products/{id} and delivers product data as JSON (where {id} will be a number. 
Example product IDs: 13860428, 54456119, 13264003, 12954218) 
•	Example response: {"id":13860428,"name":"The Big Lebowski (Blu-ray) (Widescreen)","current_price":{"value": 13.49,"currency_code":"USD"}}
•	Performs an HTTP GET to retrieve the product name from an external API. (For this exercise the data will come from redsky.target.com, but let’s just pretend this is an internal resource hosted by myRetail)  
•	Example: 
https://redsky-uat.perf.target.com/redsky_aggregations/v1/redsky/case_study_v1?key=3yUxt7WltYG7MFKPp7uyELi1K40ad2ys&tcin=13860428
•	Reads pricing information from a NoSQL data store and combines it with the product id and name from the HTTP request into a single response.  
•	BONUS: Accepts an HTTP PUT request at the same path (/products/{id}), containing a JSON request body similar to the GET response, and updates the product’s price in the data store. 

**SOLUTION:**

**Technology Stack:**

1. Spring Boot : https://spring.io/projects/spring-boot
2. Feign : https://cloud.spring.io/spring-cloud-netflix/multi/multi_spring-cloud-feign.html
3. Docker: https://www.docker.com/get-started
4. MongoDB : https://www.mongodb.com/what-is-mongodb
5. Gradle : https://gradle.org/
6. Spock Framework : http://spockframework.org/spock/docs/1.3/all_in_one.html
7. Advance Rest Client : https://github.com/advanced-rest-client
8: Design patter: Chain of responsibility

**Environment (Download/ Installation Steps):**
1. Java 1.8 (Download) : https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html
2. Intellij IDE Ultimate 2019.3
3. docker: (Download and install) https://www.docker.com/get-started
4. Grade : https://gradle.org/install/

**Running Steps:**
1. Follow above steps and install.
2. Clone or download ZIP from git project from github (git@github.com:AnandDhanasekaran/myRetail.git) and run gradle clean build command
3. Run the command to fireup the docker: docker-compose up -d
4. Import project to IDE and run spring boot application. 

**Integration test framework**

Mongodb - created using the GenericContainer
Redsky - Using wiremock server to hit the mocked client server in the port 9090

**Unit Test**
Spock test can be run from intellij by right clicking on the project - test package and select run.

**Response using postman**

Swagger results:
https://github.com/AnandDhanasekaran/myRetail/blob/master/screenshots/Swagger_snapshot.png

Postman - Insert Pricing details
https://github.com/AnandDhanasekaran/myRetail/blob/master/screenshots/Insert_pricing_details_success.png

Postman - Update pricing 
https://github.com/AnandDhanasekaran/myRetail/blob/master/screenshots/Save_pricing_details_success.png

Postman - Get product failed with Price details not available
https://github.com/AnandDhanasekaran/myRetail/blob/master/screenshots/Pricing_not_available_exception.png

postman - Get product failed with too many request from red_sky
https://github.com/AnandDhanasekaran/myRetail/blob/master/screenshots/Too_many_request_exception.png

postman - Get product failed
https://github.com/AnandDhanasekaran/myRetail/blob/master/screenshots/Product_not_found.png

postman - Get product - success
https://github.com/AnandDhanasekaran/myRetail/blob/master/screenshots/GET_PRODUCT.png


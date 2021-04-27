# NextGen-Spring-Security-With-JWT

This is a Spring boot (2.4.5v ) coded in  Java ( 1.8v) micro-service Application that uses JWT as an authentication token for User session Management. There are 4 methods implemented( /GET, /POST, /PUT, /DELETE). It expectes unexpired JWT Token, Request body in JSON format, Valid method per endpoint etc. Headover to Swagger UI to learn more about the Request/Response contract. 

It is a well-tested code with JUnit coverage of around 85% ish. The application goal is to save user data and make them readily available when users try to log in, recover, delete etc . Each exception are handeled in best way possible so that client can make rational decison before retrying. 

To make it serviceable in any work station, the Application will store the User data in Key-Value format under the main/resource properties file. The passwords are Decode using 64 bit SSL Decoder. 

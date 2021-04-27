# NextGen-Spring-Security-With-JWT

This is a Spring boot (2.4.5v ) coded in  Java ( 1.8v) micro-service Application that uses JWT as an authentication token for User session Management. There are 4 methods implemented( /GET, /POST, /PUT, /DELETE). It expected JWT unexpired Token, Request body in JSON format, and Valid method per endpoint. Please visit the Swagger UI for more details in the Request/Response contract. 

It is a well-tested code with JUnit coverage of around 85% ish. The application goal is to save user accounts credentials and make them readily available when users try to log in. The application flow is kept simple for illustrating how the JWT token works in REST API protocol.

To make it simplistic and serviceable, the Application will store the login data in Key-Value format under the main/resource properties file. The passwords are Decode using 64 bit SSL Decoder. 

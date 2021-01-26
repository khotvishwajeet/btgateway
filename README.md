# btgateway

1) module bt-gateway-service :- Run APIGatewayApplication.java which gatway for all the microservices for this example we have first and second hello world microservices for POC
   Gateway is running on the port 8080
2) module first-service :- Run FirstApplication.java which is first hello world microservice which is running on the port 8081
3) module second-service :- Run SecondApplication.java which is first hello world microservice which is running on the port 8082

Try to access both services through gateway by using below URL's 

http://localhost:8080/myfirst/message
http://localhost:8080/mySecond/message

### A Route Example
Here's a simple example of a route used in this POC

```yaml
spring:
  cloud:
    gateway:
      default-filters:
        - name: LoggingFilter
      routes:
        - id: myfirst-service
          uri: http://localhost:8081
          predicates:
            - Path=/myfirst/**
          filters:
            - AddRequestHeader=X-Request-Foo, Bar
            - AddRequestTimeHeaderPreFilter
            - AddResponseHeader=X-Response-Bye, Bye
            - AddResponseTimeHeaderPostFilter
```

## Project Synopsis 

This POC consists of two modules:
  - A **myfirst-service** is a Spring Boot based REST service which give "Hello !!! This is First Service" response. 
    It uses Basic Auth for authentication.
  
  - An **gateway-service** is a Spring Cloud Gateway service. It routes incoming requests to the 
  backend myfirst-service. It uses both Basic Auth and OAuth2 for authenticating a request. Once the request is authenticated, 
  it forwards the request to the myfirst-service after replacing the authorization header with myfirst-service basic 
  auth credentials. 

### Types of Filter
Gateway filters can be classified into 3 groups:
  
  - **Global Filters**: They are special filters that are conditionally applied to all routes. A good use of a global
    filter can be authentication of an incoming request. Here are the global filters used in this example:
    
    - **Authorization Filter**: A custom filter for authenticating a request. Authentication can be Basic Auth or OAuth2.
    
    - **Basic Auth Token Relay Filter**: A custom filter whi h replaces the authorization header with basic auth credentials 
    specific to a route.
    
  - **Pre Filters**: These filters are applied to incoming requests and are usually specific to a route. Here are the 
  pre filters used in this example:
  
    - **Prefix Filter**: A built-in filter which adds a prefix to the incoming request before being forwarded.
    
    - **Add Request Header Filter**: A built-in filter which adds a header to the incoming request before being forwarded.
    
    - **Add Request Time Header Pre Filter**: A custom filter which add a timestamp header to the incoming request before being forwarded.
  
  - **Post Filters**: These filters are applied to outgoing request and are usually specific to a route. Here are the 
  post filters used in this example:
  
    - **Add Response Header Filter**: A built-in filter which adds a header to the outgoing response.
      
    - **Add Response Time Header Post Filter**: A custom filter which add a timestamp header to the outgoing response.
  
## Security
This POC didn't use Spring Security framework directly as typically used in a Spring Boot application by configuring the
 service. However, it took advantage of the classes provided in the Spring Security libraries to come up with a custom security
 framework. This example uses both **Basic Authentication** and **OAuth 2.0** for authentication. 
 
### Basic Authentication
The basic authorization credentials are configured using `security.auth.user` and `security.auth.password` in the 
 `application.yml`.
 
### OAuth2 Authentication
The OAuth2 uses `JSON Web Token` (`JWT`) for authentication. The client of the Edge service uses `Client Credentials` 
grant type to obtain an access token from an auth server. We need to decide which Auth0 tool will integrate for 
obtaining an access token.
        
You need to configure the `application.yml` before using the `Auth0` JWT. You have to set the following properties:

```yaml
spring:
  security:
    oauth2:
      resourceserver:
        jwt:
          issuer-uri: <<https://ibasak.auth0.com/>>
          audience: <<https://quickstarts/api>>
```

#### Okta Authorization Server                                                      
Here's an example to obtain an access token from an `Okta` server:

```
curl --request POST \
  --url https://dev-461512.okta.com/oauth2/default/v1/token \
  --header 'accept: application/json' \
  --header 'authorization: Basic MG9hMWFhMXI0MzB3NEFLajczNTc6b0gwOTB0RlZFVG5iV0o4eElQX3BMOXV6SWRmV2h0MWNISjdWb1d0VQ==' \
  --header 'cache-control: no-cache' \
  --header 'content-type: application/x-www-form-urlencoded' \
  --data grant_type=client_credentials \
  --data scope=customScope
```

A response usually looks something similar to this:

```json
{
  "token_type":"Bearer",
  "expires_in":3600,
  "access_token":"eyJraWQiOiItM1N6UVhjWDNYc0lFMmNOSnZ6NGRZRzBZemdXVS1Od091THpxYmZ1cWQ0IiwiYWxnIjoiUlMyNTYifQ.eyJ2ZXIiOjEsImp0aSI6IkFULkluNHlfNVJQd0N4eHBURkVmVThGRERiSEQyZHF6Q1RiNjBreGxPaDhpMjgiLCJpc3MiOiJodHRwczovL2Rldi00NjE1MTIub2t0YS5jb20vb2F1dGgyL2RlZmF1bHQiLCJhdWQiOiJhcGk6Ly9kZWZhdWx0IiwiaWF0IjoxNTY3ODA2OTk0LCJleHAiOjE1Njc4MTA1OTQsImNpZCI6IjBvYTFhYTFyNDMwdzRBS2o3MzU3Iiwic2NwIjpbImN1c3RvbVNjb3BlIl0sInN1YiI6IjBvYTFhYTFyNDMwdzRBS2o3MzU3IiwiQ2xhaW0xIjpmYWxzZX0.TShTVtfRp8wU39NY40KpTo1PCLB8N2x3kuVdkgJVYvU5zd5yBkz3RZZLksqsWQEfirAKduBdSkF4aMQhBUo3tdDYefQ6TNqnun_Ung1f3TdUAalyqeUgpGGlbN2J93jv-djtF5O7ylElpKqvwXhZcwXhJb1HPJqLB_LP0XtaxDb5R8uPP56IhE6JEC8PCIvpMOM0gr9mYsJWxwTe-tVd5NHUTSIaDBtMCsFbcx8MkG6YXN0N-B1ZsyZJMHBA8nwWk1Fx7EbIyxTmpUQdnBmwP-YM1XNCvBZQkX9BhId6YnaAjmLhJ_SQB1VWew28oAHpeax9Lkj-R49rzqxsjcTvVA",
  "scope":"customScope"
}
```

You need to configure the `application.yml` before using the `Okta` JWT. You have to set the following properties:

```yaml
spring:
  security:
    oauth2:
      resourceserver:
        jwt:
          issuer-uri: https://dev-461512.okta.com/oauth2/default
          audience: api://default
```
If the build is successful 

## Starting Applications
You can start both the applications from the terminal by typing the following command:

```yaml
java -jar bt-gateway-service-0.0.1-SNAPSHOT.jar
```

The bt gateway service should start up at port `8080`

```
java -jar first-service-0.0.1-SNAPSHOT.jar
```

The first-service should start up  at port `8081`


## Usage

### Basic Authentication
To create a first service using basic authentication:
```
curl --request GET --url http://localhost:8080/myfirst/message --header 'authorization: Basic dmlzaHdhOnRlc3Q=' --header 'content-type: application/json'

```


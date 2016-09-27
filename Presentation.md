<img align="right" src="img/cphbusinessWhite.png" />  
#Restful web services with JAX-RS
##Day 1: 
###Studymaterial:
[Tutorials point on ReST](https://www.tutorialspoint.com/restful/index.htm) 
**Read the 10 points under headline: RESTful Tutorial** (You can skip 3 and 4 about env and first application)  
[Tutorial on how to manually test rest api with postman app](https://blog.matrix42.com/2016/08/08/api-testing-postman-part-1/) 
[Nice list of simple examples using Jax-RS](http://www.mkyong.com/tutorials/jax-rs-tutorials/)


###Definition of web service
"*a method of communication between two electronic devices over a network. 
It is a software function provided at a network address over the web, with the service always on.*"



###Restful webservice
- HTTP transport protocol
- No specific Data Protocol



###Alternatives to Restful webservices
- SOAP
	- Simple Object Access Protocol
	- [Read more here](https://en.wikipedia.org/wiki/SOAP)
- WSDL
	- Web Service Description Language
	- [Read more here](http://www.w3schools.com/xml/xml_wsdl.asp)



###REST - REpresentational State Transfer 
- Representational
    - Clients possess the information necessary to identify, modify, and/or delete a web resource.
- State
    - All resource state information is stored on the client.
- Transfer
    - Client state is passed from the client to the service through HTTP.


### Demo
<img align="right" src="img/demoman.png" />  
- Start new maven project
- import the relevant dependencies to POM.xml
	- [Here is an example](https://dzone.com/articles/build-rest-service-netbeans-7)
- Create a 'New Restful webservice from pattern'
- Change the path to: api/myresponse
- Change the @Get method to just send a hello world message.
- Test it from the browser.
- Test it from Postman



###Postman
- Install
- Introduction to the tool



###Rest and HTTP methods
- GET - Provides a read only access to a resource.
- PUT - Used to create a new resource.
- DELETE - Used to remove a resource.
- POST - Used to update a existing resource or create a new resource.

![](img/restmethods.png)



### Webressources in REST
- Refers to anything you can get from the server
- Most likely in the form of
	- text
	- XML or
	- JSON (What we will use)
- Web ressource is
	- An object in OOP
	- An entity in DB
- **IMPORTANT**: Rest has **no FORMAT restriction**
	- Means that we have to make it intuitive ourselfes (and/or write an API documentation)


### HTTP messaging

##### HTTP REQUEST
![](img/http_request.jpg)  

- Verb
	- Indicate HTTP methods such as GET, POST, DELETE, PUT etc.
- URI
	- Uniform Resource Identifier (URI) to identify the resource on server
- HTTP Version
	- Indicate HTTP version, for example HTTP v1.1 .
- Request Header
	- Contains metadata for the HTTP Request message as key-value pairs. For example, client ( or browser) type, format supported by client, format of message body, cache settings etc.
- Request Body
	- Message content or Resource representation.



#####HTTP RESPONSE
![](img/http_response.jpg)  

- Status/Response Code
	- Indicate Server status for the requested resource. For example 404 means resource not found and 200 means response is ok.
- HTTP Version
	- Indicate HTTP version, for example HTTP v1.1 .
- Response Header
	- Contains metadata for the HTTP Response message as key-value pairs. For example, content length, content type, response date, server type etc.
- Response Body
	- Response message content or Resource representation.

###Lets demo
<img align="right" src="img/demoman.png" />  
- use chrome to access: `http://46.101.253.187:8080/quotes/api/quote/1`
- check network tab in chrome developer tools to see the HTTP messages


##Exercise day1:
[Click here for the daily exercise](https://github.com/CphBusCosSem3/Exercises/blob/master/SP/SP5/REST_JAX-RS_ex1.pdf)

##Day2: error handling
###Studymaterial
[Read section 7.3](https://jersey.java.net/documentation/latest/representations.html)
[simple example](http://howtodoinjava.com/resteasy/exception-handling-in-jax-rs-resteasy-with-exceptionmapper/)


###The 2 things that can make a rest api fail
1. The client makes a call the REST API is not designed to handle
	- call a not existing URI, provide a wrong Content-Type etc.)
2. The Backend Business Logic throws an exception (Checked or Unchecked)
	- E.g: CustomerNotFoundException or a NullPointerException
	- [See reference on java Exceptions here](http://www.hacktrix.com/checked-and-unchecked-exceptions-in-java)  



### REST and error handling
Rest service should  
1. provide a useful error message in a known consumable format  
2. The representation of an error should be no different than the representation of any resource, just with its own set of fields  
3. The API should always return sensible HTTP status codes. API errors typically break down into 2 types:  
      1. 400 series status codes for client issues  
      2. 500 series status codes for server issues.  
4. If Json is used for DTOs - we use Json for error msg  
5. [Best practises](http://www.vinaysahni.com/best-practices-for-a-pragmatic-restful-api)  



###HTTP status codes
Use the http status codes in error message to the client:  
like: {"status": 404, "message": "The ressource was not found on the server"}  
[See list of all HTTP status codes](http://www.restapitutorial.com/httpstatuscodes.html)  


###Mapping java exceptions to json objects
When the Rest Service throws an exception either:  
1. checked (java.lang.Exception)  
2. unchecked (java.lang.RuntimeException)
We can do either  
    1. Catch the exception and return a Response object  
	    - `Response.status(Response.Status.LENGTH_REQUIRED).build();`  
    2. Create Exception mappers  
	    - can convert an exception to an HTTP response  
	    - (If the thrown exception is not handled by a mapper, it is propagated and handled by the container (i.e., servlet) JAX-RS is running within - and we get a generated html response)  
	    - [See example section 7.3](https://jersey.java.net/documentation/latest/representations.html)  
    3. JAX-RS provides the WebApplicationException
	    - Then there is no need for explicit Exception Mapper  
	    - This exception is pre initialized with either a Response or a particular status code  
	    ```java 
	    @GET
		@Path("{id}")
		@Produces("application/xml")
		public Customer getCustomer(@PathParam("id") int id) {
		  Customer cust = findCustomer(id);
		  if (cust == null) {
		    throw new WebApplicationException(Response.Status.NOT_FOUND);
		  }
		  return cust;
		}
	    ```


### PROs and CONs of the 3 approaches
- When you can't return the regular response, you must return an error response to the client. 
  - You can do that with either throwing the exception (2 or 3) or building the response by hand (1). 
  - It's the same thing for the client!
    - but it's not the same thing for the server side code.
  - Throwing the exception makes your code cleaner and easier to understand. 
  - Using option 3 - The idea is to subclass the WebApplicationException and create meaningful exceptions out of it
    - e.g ProductNotFoundException extends WebApplicationException { ... }  
    - AccessDeniedException extends WebApplicationException { ... } 
   - Using option 2 means that we canvreuse existing exceptions and let the mappers handle the convertion of the response).
     - It's then cleaner to throw new ProductNotFoundException() or throw new AccessDeniedException() and let the framework handle it instead of building a Response every time and later follow the details used to build it to figure out what's happening in that section of code.


###Exercise day2:
There are 2 exercises:  
1. [Rest service with JQuery and AJAX](https://github.com/CphBusCosSem3/Exercises/blob/master/SP/SP5/REST_JAX-RS_ex2.pdf)  
2. [Errorhandling](https://github.com/CphBusCosSem3/Exercises/blob/master/SP/SP5/REST_JAX-RS_ex3_errorhandling.pdf)

##Day3: Deployment
###Deploy a full application on Digital Ocean
Follow this guide (It is both the class material and the home work)
[Exercise 4](https://github.com/CphBusCosSem3/Exercises/blob/master/SP/SP5/REST_ex4-DigitalOcean.pdf)





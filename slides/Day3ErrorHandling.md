<img align="right" src="../img/cphbusinessWhite.png"/>
##Day3: Error handling with Exception Mappers
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
- We can do either  
    1. Catch the exception and return a Response object  
	    - `Response.status(500).entity("message").build();`  
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



/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.google.gson.Gson;
import entities.Person;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 * REST Web Service
 *
 * @author Thomas Hartmann - tha@cphbusiness.dk
 */
@Path("test") //Test this with url : localhost:port/<application>/api/test
public class TestResource {

    private Gson gson = new com.google.gson.GsonBuilder().setPrettyPrinting().create();
    @Context
    private UriInfo context;

    /**
     * Creates a new instance of TestResource
     */
    public TestResource() {
    }

    /**
     * Simple example
     * Shows how to request a resource with the class/root resource path
     */
    @GET
    public String getText() {
        return "This is the first and most simple test";
    }
    
    //Getting a subresource - by using the @Path annotation on the method:
    @GET
    @Path("/subresource")
    public String getSubResource(){
        return "This is a test using a sub resource - from a method annotated with @Path";
    }
    
    //Sending out json from the webservice
    @GET
    @Path("/getjson")
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson(){
        return "{\"name\":\"Trine Henriksen\"}";
    }
    
    //Using the Response object to return status and message:
    @GET
    @Path("/getresponse")
    public Response getResponse(){
        return Response.status(200).entity("Some method here that could be formated json").build();
    }
    
    //Returning response and using gson to format as json
    @GET
    @Path("/json/getresponse")
    public Response getJsonResponse(){
        Person p = new Person("Hanne Kruse", 51);
        String str = gson.toJson(p);
        Response r = Response.status(200).entity(str).build();
        return r;
    }
    
    //Send input parameter to the get request test with: /api/json/someparameter
    //if the parameter equals the name of another methods path this will overwrite the parameter (like using  /api/json/getresponse from the previous method
    @GET
    @Path("/json/{input}")
    public Response getFromInput(@PathParam("input") String input){
        return Response.status(Response.Status.OK).entity("The input is: "+input).build();
    }
    
    // Using 2 or more inputs
    @GET
    @Path("/json/mul/{n1}/{n2}")
    public Response getFromMultipleInput(@PathParam("n1") int n1, @PathParam("n2") int n2){
        return Response.status(200).entity("Result when multiplying: "+ (n1*n2)).build();
    }

    // Using Post json object formatted data to the server
    @POST
    @Path("/post")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response addPerson(String jsonPerson) {
        //Use gson to convert from json to java Person object
        Person p = gson.fromJson(jsonPerson, Person.class);
        p.setPhone("+4544998877");
        return Response
                .status(200)
                .entity(gson.toJson(p))
                .build();
    }
    
    @POST
    @Consumes("application/x-www-form-urlencoded")
    public String post(MultivaluedMap<String, String> formParams) {
        return formParams.getFirst("name");
    }

    //In this example I am using jettison to get parameters out of a json object (See the pom.xml)
    @DELETE //This method must have a String input parameter. 
    @Consumes(MediaType.APPLICATION_JSON)
    public String deleteSomething(String input) throws JSONException {
        JSONObject job = new JSONObject(input);
        String name = job.optString("name");
        String age = job.optString("age");
        return name + " of " + age;
    }
}

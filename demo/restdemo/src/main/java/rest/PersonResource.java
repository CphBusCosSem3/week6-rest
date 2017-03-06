/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.google.gson.Gson;
import entities.Person;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Path;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author Thomas Hartmann - tha@cphbusiness.dk
 */
@Path("person")
public class PersonResource {

    private static Map<Integer, Person> persons = new HashMap();

    static {
        persons.put(1, new Person("Helle", 23));
        persons.put(2, new Person("Hanne", 32));
        persons.put(3, new Person("Heidi", 54));
        persons.put(4, new Person("Hansine", 2));
    }
    private Gson gson = new com.google.gson.GsonBuilder().setPrettyPrinting().create();
    @Context
    private UriInfo context;

    /**
     * Creates a new instance of PersonResource
     */
    public PersonResource() {
    }
    //private ExecutorService executorService = java.util.concurrent.Executors.newCachedThreadPool();
//
//    //Example1: simple (no parameters and media type: text)
//    //This method runs when server recieves a get request like:
//    //http://localhost:8084/restdemo/api/person
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getText() {
//        JsonObject job = new JsonObject();
//        job.addProperty("quote", quote);
        return "Hello From REST";
    }

    //Ex2: using @Path and @PathParam to provide an input to the method
    //Request like: http://localhost:8084/restdemo/api/person/<some name>
    @GET
    @Path("{name}")
    @Produces(MediaType.TEXT_PLAIN)
    public String getText(@PathParam("name") String name) {
        return "Hello " + name + " From REST";
    }
    
//    @GET
//    @Path("{id}")
//    @Produces(MediaType.TEXT_PLAIN)
//    public String getPerson(@PathParam("id") int id){
//        return persons.get(1).getName();
//    }
//
//    //Ex3: Using the Response object
//    //Test with: http://localhost:8084/restdemo/api/person/resp
    @GET
    @Path("/resp")
    public Response getResp() {
        Response res = Response.status(200).entity("Hello world from Response object").build();
        return res;
    }
//
//    //Ex4: Using key value (username={username})
//    //test with http://localhost:8084/restdemo/api/person/username/tha123
    @GET
    @Path("/username/{username}")
    public Response getPersonByName(@PathParam("username") String username) {

        return Response.status(200).entity("getUserByUserName is called, username : " + username).build();
    }
//
//    //Ex5: Using regexp to validate input
//    //Using gson to create JSON from java object (Type: Person)
//    //test with: http://localhost:8084/restdemo/api/person/id/3
    @GET
    @Path("/id/{id : \\d+}") //only allow numbers (d for digit)
    @Produces({MediaType.APPLICATION_JSON})
    public Response getPersonById(@PathParam("id") int id) {
        Person p = persons.get(id);
        String jsonPerson = gson.toJson(p);
        return Response.status(Response.Status.OK).entity(jsonPerson).build();
    }
//
//    //Ex6: Using multiple input parameters
//    //test with: http://localhost:8084/restdemo/api/person/22/52
    @GET
    @Path("{minage}/{maxage}")
    public Response getUserHistory(
            @PathParam("minage") int min,
            @PathParam("maxage") int max) {
        List<Person> personList = new ArrayList();
        for (Map.Entry<Integer, Person> entry : persons.entrySet()) {
            int age = entry.getValue().getAge();
            if(age > min && age < max)
                personList.add(entry.getValue());
        }
        String json = gson.toJson(personList);
        return Response.status(Response.Status.OK)
                .entity(json)
                .build();

    }
//
//    //Use post to add or update data on the server
//    //Test this method from Postman with a post request containing a simple JSON object in the body like
//    //{"name": "Henriette", "age": 32}   //Remember to set 
//    //post request: http://localhost:8084/todelete/api/person/
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response addPerson(String jsonPerson) {
        
        //Use gson to convert from json to java Person object
        Person p = gson.fromJson(jsonPerson, Person.class);
        int id = persons.size()+1;
        persons.put(id, p);
        return Response
                .status(200)
                .entity(gson
                        .toJson(persons.get(id)))
                .build();
    }
//    
//    @POST
//    @Consumes({MediaType.APPLICATION_JSON})
//    @Produces({MediaType.APPLICATION_JSON})
//    public Response testReturn(String jsonPerson) {
//        //JsonObject job;
//        throw new NotImplementedException();
//    }

}

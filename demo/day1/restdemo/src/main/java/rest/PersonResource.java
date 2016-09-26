/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import com.google.gson.Gson;
import entities.Person;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

/**
 * REST Web Service
 *
 * @author Thomas Hartmann - tha@cphbusiness.dk
 */
@Path("person")
public class PersonResource {
    private Gson gson = new com.google.gson.GsonBuilder().setPrettyPrinting().create();
    @Context
    private UriInfo context;

    /**
     * Creates a new instance of PersonResource
     */
    public PersonResource() {
    }
    
    //This method runs when server recieves a get request like:
    //http://localhost:8084/restdemo/api/person
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getText() {
        return "Hello From REST";
    }
    
    //Request like: http://localhost:8084/restdemo/api/person/<some name>
    @GET
    @Path("{name}")
    @Produces(MediaType.TEXT_PLAIN)
    public String getText(@PathParam("name") String name) {
        return "Hello "+name+" From REST";
    }

    
    //Use post to add or update data on the server
    //Test this method from Postman with a post request containing a simple JSON object in the body like
    //{"name": "Jesper"}
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.TEXT_PLAIN})
    public String postText(String person) {
        //Use gson to convert from json to java Person object
        Person p = gson.fromJson(person, Person.class);
        return p.getName();
        
    }
}

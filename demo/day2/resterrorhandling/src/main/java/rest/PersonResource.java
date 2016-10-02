/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import exception.ErrorMessage;
import exception.MyException;
import exception.PersonNotFoundException;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author Thomas Hartmann - tha@cphbusiness.dk
 */
@Path("person")
public class PersonResource {
    Gson gson = new GsonBuilder().create();
    @Context
    private UriInfo context;

    /**
     * Creates a new instance of PersonResource
     */
    public PersonResource() {
    }

    @GET
    @Path("/basic")
    public String getSomething() {
        return "Something";
    }
    //Example using a specific exceptionMapper
    @GET
    @Path("/exception/personnotfound")
    public String testPNFex() throws PersonNotFoundException {
        throw new PersonNotFoundException("No person by that id");
    }

    //Example using the general exceptionMapper
    @GET
    @Path("/exception/generalexception")
    public String testGeneralex() throws Exception {
        throw new Exception("This general exception was thrown from testGeneralex()");
    }
    
    //One way to handle exceptions is to catch them and return an appropriate error message.
    @GET
    @Path("/exception/myexception")
    public Response testMyExc() throws MyException{
        try{
            throw new MyException("MyException was thrown from the PersonRessource class");
        }catch(MyException mye){
            ErrorMessage em = new ErrorMessage(mye, 404, false);
            String jsonEx = gson.toJson(em);
            return Response.status(Response.Status.NOT_FOUND).entity(jsonEx).build();
        }
    }
    
    //Using webException
    //These webexceptions is thrown instead of specific exceptions like PersonNotFoundException
    //This is possible if every needed exception is translated into an exception that extends the webexception.
}

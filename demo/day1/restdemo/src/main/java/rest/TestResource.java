/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.google.gson.Gson;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 * REST Web Service
 *
 * @author Thomas Hartmann - tha@cphbusiness.dk
 */
@Path("test")
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
     * Retrieves representation of an instance of rest.TestResource
     *
     * @return an instance of java.lang.String
     */
    @GET
    public String getText() {
        return "This is the first and most simple test";
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

//    @Consumes(MediaType.APPLICATION_JSON)
//    //@Produces(MediaType.APPLICATION_JSON)
//    public String putJson(JSONObject jsonobj) {
//        String name = jsonobj.optString("name");
//        String age = jsonobj.optString("age");
//        return name + " " + age;
//    }
}

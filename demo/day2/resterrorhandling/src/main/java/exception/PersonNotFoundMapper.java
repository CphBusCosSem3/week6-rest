package exception;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javax.servlet.ServletContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author Thomas Hartmann - tha@cphbusiness.dk created on Sep 27, 2016 
 */
@Provider
public class PersonNotFoundMapper implements ExceptionMapper<PersonNotFoundException> {
    static Gson gson = new GsonBuilder().setPrettyPrinting().create();
    @Context 
    ServletContext context;
    
    @Override
    public Response toResponse(PersonNotFoundException ex) {
       boolean isDebug = context.getInitParameter("debug").equals("true");
       ErrorMessage err = new ErrorMessage(ex,404,isDebug);
       err.setDescription("You tried to call ...");
       return Response.status(404)
				.entity(gson.toJson(err))
				.type(MediaType.APPLICATION_JSON).
				build();
	}
}
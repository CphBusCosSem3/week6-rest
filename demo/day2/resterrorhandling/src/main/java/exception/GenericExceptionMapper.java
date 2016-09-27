package exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author Thomas Hartmann - tha@cphbusiness.dk created on Sep 27, 2016 
 */
@Provider
public class GenericExceptionMapper implements ExceptionMapper<Exception>{

    @Override
    public Response toResponse(Exception exception) {
        return Response.status(500).entity(exception.getMessage()).build();
    }
    
}

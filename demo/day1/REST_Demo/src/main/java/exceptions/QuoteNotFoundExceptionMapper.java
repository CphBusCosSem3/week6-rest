package exceptions;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class QuoteNotFoundExceptionMapper implements ExceptionMapper<QuoteNotFoundException> {

  static Gson gson = new GsonBuilder().setPrettyPrinting().create();

  @Override
  public Response toResponse(QuoteNotFoundException ex) {
    ErrorMessage em = new ErrorMessage(ex, 404);
    return Response.status(Response.Status.NOT_FOUND).entity(gson.toJson(em)).type(MediaType.APPLICATION_JSON).build();
            
  }

}

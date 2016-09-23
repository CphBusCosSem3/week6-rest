package exceptions;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class AllExceptionMapper implements ExceptionMapper<Throwable> {

  static Gson gson = new GsonBuilder().setPrettyPrinting().create();

  @Override
  public Response toResponse(Throwable ex) {
    ErrorMessage em = new ErrorMessage(ex, 500);
    System.out.println("XXXXXXXXXXXXXXXXX");
    em.setMessage("Internal server Error, we are very sorry for the inconvenience");
    return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(gson.toJson(em)).type(MediaType.APPLICATION_JSON).build();
  }

}

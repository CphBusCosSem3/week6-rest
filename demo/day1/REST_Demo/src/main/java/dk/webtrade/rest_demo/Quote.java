package dk.webtrade.rest_demo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import exceptions.QuoteNotFoundException;
import java.util.HashMap;
import java.util.Map;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import static javax.ws.rs.HttpMethod.POST;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author plaul1
 */
@Path("quote")
public class Quote {

  private static Gson gson = new GsonBuilder().setPrettyPrinting().create();
  private static int lastID = 4;
  private static Map<Integer, String> quotes = new HashMap() {
    {
      put(1, "Friends are kisses blown to us by angels");
      put(2, "Do not take life too seriously. You will never get out alive");
      put(3, "Behind every great man, is a woman rolling her eyes");
    }
  };

  @Context
  private UriInfo context;

  public Quote() {
  }

  @GET
  @Path("{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getQuote(@PathParam("id") int id) throws QuoteNotFoundException {
    if(id==5){
      throw new NullPointerException("UUUPS");
    }
    String quote = quotes.get(id);
    if(quote==null){
      throw new QuoteNotFoundException("Quote with requested id not found");
    }
    JsonObject json = new JsonObject();
    json.addProperty("quote",quote );
    return Response.ok(gson.toJson(json)).build();
  }

  @GET
  @Path("random")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getRandomQuote() throws QuoteNotFoundException {
    if (quotes.size() == 0) {
      throw new QuoteNotFoundException("No Quotes Created yet");
    }
    int id = (int) Math.ceil(Math.random() * (quotes.size()));
    JsonObject json = new JsonObject();
    json.addProperty("quote", quotes.get(id));
    json.addProperty("id", id);
    return Response.ok(gson.toJson(json)).build();
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response addQuote(String json) {
    JsonObject jo = new JsonParser().parse(json).getAsJsonObject();
    String editedQuote = jo.get("quote").getAsString();
    quotes.put(++lastID, editedQuote);
    jo.addProperty("id", lastID);
    return Response.ok(gson.toJson(jo)).build();
  }

  @PUT
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @Path("{id}")
  public Response editQuote(@PathParam("id") int id, String json) throws QuoteNotFoundException {
    String quote = quotes.get(id);
    if (quote == null) {
      throw new QuoteNotFoundException("Quote with requested id not found");
    }
    JsonObject jo = new JsonParser().parse(json).getAsJsonObject();
    String editedQuote = jo.get("quote").getAsString();
    quotes.put(id, editedQuote);
    jo.addProperty("id", id);
    return Response.ok(gson.toJson(jo)).build();
  }

  @DELETE
  @Produces(MediaType.APPLICATION_JSON)
  @Path("{id}")
  public Response deleteQuote(@PathParam("id") int id) throws QuoteNotFoundException {
    String quote = quotes.get(id);

    if (quote == null) {
      throw new QuoteNotFoundException("Quote with requested id not found");
    }
    quotes.remove(id);
    JsonObject jo = new JsonObject();
    jo.addProperty("id", id);
    jo.addProperty(quote, quote);
    return Response.ok(gson.toJson(jo)).build();
  }
}

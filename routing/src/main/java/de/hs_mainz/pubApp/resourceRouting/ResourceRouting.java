package de.hs_mainz.pubApp.resourceRouting;

import com.google.gson.Gson;
//import de.hs_mainz.pubApp.HttpApiRequest;
import com.google.gson.JsonParser; // TODO remove
import de.hs_mainz.pubApp.HttpApiRequestRouting;
import de.hs_mainz.pubApp.jsonparser.ClientInputJsonRouting;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 * Created by Sarah.
 */
@Path("routing")
public class ResourceRouting {

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String queryText(@QueryParam("queryText") String queryText){
        Gson gson = new Gson();

        ClientInputJsonRouting inputJson = gson.fromJson(queryText, ClientInputJsonRouting.class);

        HttpApiRequestRouting httpApiRequest = new HttpApiRequestRouting();
//        String out = gson.toJson(httpApiRequest.requestGraphhopperGeocoder(inputJson));
//        String out = gson.toJson(new JsonParser().parse(httpApiRequest.requestGraphhopperRouting(inputJson)).getAsJsonObject());

        return httpApiRequest.requestGraphhopperRouting(inputJson);
    }
}

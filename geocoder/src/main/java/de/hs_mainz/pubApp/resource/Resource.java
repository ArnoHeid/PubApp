package de.hs_mainz.pubApp.resource;


import com.google.gson.Gson;
import de.hs_mainz.pubApp.HttpApiRequest;
import de.hs_mainz.pubApp.jsonparser.ClientInputJson;


import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;


/**
 * Created by Arno on 03.12.2016.
 */
@Path("geocoder")
public class Resource {

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String geoCoder(@QueryParam("text") String text){
        Gson gson = new Gson();

        ClientInputJson inputJson = gson.fromJson(text, ClientInputJson.class);

        HttpApiRequest httpApiRequest = new HttpApiRequest();
        String out = gson.toJson(httpApiRequest.requestGraphhopperGeocoder(inputJson));

        return out;
    }
}
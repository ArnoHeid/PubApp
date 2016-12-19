package de.hsmainz.pubApp.resourceRouting;

import com.google.gson.Gson;
//import de.hs_mainz.pubApp.HttpApiRequest;
import de.hsmainz.pubApp.HttpApiRequestRouting;
import de.hsmainz.pubApp.jsonparser.ClientInputJsonRouting;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Created by Sarah.
 */
@Path("routing")
public class ResourceRouting extends ResourceRoutingTemplate {

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String queryText(
            @QueryParam("startPoint") String startPoint,
            @QueryParam("endPoint") String endPoint,
            @DefaultValue("de") @QueryParam("locale") String locale,
            @DefaultValue("false") @QueryParam("pointsEncoded") String pointsEncoded,
            @DefaultValue("") @QueryParam("callback") String callback){
        Gson gson = new Gson();

        HttpApiRequestRouting httpApiRequest = new HttpApiRequestRouting();

        return jsonCallbackWrapper(callback, httpApiRequest.requestGraphhopperRouting(startPoint, endPoint, locale, pointsEncoded));
    }
}

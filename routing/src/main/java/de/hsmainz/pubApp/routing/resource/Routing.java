package de.hsmainz.pubApp.routing.resource;

import com.google.gson.Gson;
import de.hsmainz.pubApp.routing.httpApiRequest.HttpAPIRequest;
import de.hsmainz.pubApp.routing.httpApiRequest.HttpGraphhopperRequest;
import de.hsmainz.pubApp.routing.jsonparser.geoJson.GeoJsonCollection;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Created by Sarah.
 */
@Path("routing")
public class Routing extends RoutingTemplate {

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
            @QueryParam("locale") @DefaultValue("de") String locale,
            @QueryParam("pointsEncoded") @DefaultValue("false") String pointsEncoded,
            @QueryParam("callback") @DefaultValue("")  String callback){
        Gson gson = new Gson();

        HttpAPIRequest httpApiRequest = new HttpGraphhopperRequest();

        GeoJsonCollection geoJsonColection = httpApiRequest.requestRouting(startPoint, endPoint, locale, pointsEncoded);

        return jsonCallbackWrapper(callback, gson.toJson(geoJsonColection));
    }
}

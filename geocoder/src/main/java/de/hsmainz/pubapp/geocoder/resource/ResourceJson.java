package de.hsmainz.pubapp.geocoder.resource;


import com.google.gson.Gson;
import de.hsmainz.pubapp.geocoder.httpApiRequest.HttpAPIRequest;
import de.hsmainz.pubapp.geocoder.httpApiRequest.HttpGraphhopperRequest;
import de.hsmainz.pubapp.geocoder.jsonparser.ClientInputJson;
import de.hsmainz.pubapp.geocoder.jsonparser.geoJson.GeoJsonColection;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;


/**
 * Created by Arno on 03.12.2016.
 */
@Path("geocoder/json")
public class ResourceJson extends ResourceTemplate{

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String geoCoder(@QueryParam("callback") String callback, @QueryParam("queryText") @DefaultValue("de") String queryText){
        Gson gson = new Gson();

        ClientInputJson inputJson = gson.fromJson(queryText, ClientInputJson.class);

        HttpAPIRequest httpAPIRequest = new HttpGraphhopperRequest();
        GeoJsonColection geoJsonColection = httpAPIRequest.requestGeocoder(inputJson);

        return jsonCallbackWraper(callback, gson.toJson(geoJsonColection));
    }

}
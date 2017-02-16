package de.hsmainz.pubapp.geocoder.web;


import com.google.gson.Gson;
import de.hsmainz.pubapp.geocoder.controller.HttpAPIRequest;
import de.hsmainz.pubapp.geocoder.controller.HttpAPIRequesterFactory;
import de.hsmainz.pubapp.geocoder.model.ClientInputJson;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;


/**
 * Resource for use with query JSON
 *
 * @author Arno
 * @since 03.12.2016.
 */
@Path("geocoder/json")
public class ResourceJson extends ResourceTemplate {

    //****************************************
    // CONSTANTS
    //****************************************

    //****************************************
    // VARIABLES
    //****************************************

    //****************************************
    // INIT/CONSTRUCTOR
    //****************************************

    //****************************************
    // GETTER/SETTER
    //****************************************

    //****************************************
    // PUBLIC METHODS
    //****************************************

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String geoCoder(@QueryParam("callback") String callback, @QueryParam("queryText") String queryText) {
        Gson gson = new Gson();
        ClientInputJson inputJson = gson.fromJson(queryText, ClientInputJson.class);
        HttpAPIRequest httpAPIRequest = HttpAPIRequesterFactory.createRequest(inputJson.getApi());
        String geoJson = httpAPIRequest.requestGeocoder(inputJson);
        return jsonCallbackWraper(callback, geoJson);
    }

    //****************************************
    // PRIVATE METHODS
    //****************************************

    //****************************************
    // INNER CLASSES
    //****************************************

}
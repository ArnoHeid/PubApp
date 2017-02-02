package de.hsmainz.pubapp.geocoder.resource;


import com.google.gson.Gson;
import de.hsmainz.pubapp.geocoder.httpapirequest.HttpAPIRequest;
import de.hsmainz.pubapp.geocoder.httpapirequest.HttpGraphhopperRequest;
import de.hsmainz.pubapp.geocoder.jsonparser.ClientInputJson;

import javax.ws.rs.*;
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
    public String geoCoder(@QueryParam("callback") String callback, @QueryParam("queryText") @DefaultValue("de") String queryText) {
        Gson gson = new Gson();
        ClientInputJson inputJson = gson.fromJson(queryText, ClientInputJson.class);
        HttpAPIRequest httpAPIRequest = new HttpGraphhopperRequest();
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
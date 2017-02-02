package de.hsmainz.pubapp.geocoder.resource;


import de.hsmainz.pubapp.geocoder.httpapirequest.HttpAPIRequest;
import de.hsmainz.pubapp.geocoder.httpapirequest.HttpGraphhopperRequest;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;


/**
 * Resource for use with query strings
 *
 * @author Arno
 * @since 03.12.2016.
 */
@Path("geocoder")
public class Resource extends ResourceTemplate {

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
    public String geoCoder(@QueryParam("callback") String callback, @QueryParam("queryString") String queryString, @QueryParam("locale") @DefaultValue("de") String locale) {
        HttpAPIRequest httpAPIRequest = new HttpGraphhopperRequest();
        String geoJson = httpAPIRequest.requestGeocoder(queryString, locale);
        return jsonCallbackWraper(callback, geoJson);
    }

    //****************************************
    // PRIVATE METHODS
    //****************************************

    //****************************************
    // INNER CLASSES
    //****************************************

}
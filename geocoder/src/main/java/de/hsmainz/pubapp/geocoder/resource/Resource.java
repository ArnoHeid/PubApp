package de.hsmainz.pubapp.geocoder.resource;


import com.google.gson.Gson;
import de.hsmainz.pubapp.geocoder.httpapirequest.HttpAPIRequest;
import de.hsmainz.pubapp.geocoder.httpapirequest.HttpGraphhopperRequest;
import de.hsmainz.pubapp.geocoder.jsonparser.geojson.GeoJsonCollection;


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
        Gson gson = new Gson();

        HttpAPIRequest httpAPIRequest = new HttpGraphhopperRequest();

        GeoJsonCollection geoJsonCollection = httpAPIRequest.requestGeocoder(queryString, locale);

        return jsonCallbackWraper(callback, gson.toJson(geoJsonCollection));
    }

    //****************************************
    // PRIVATE METHODS
    //****************************************

    //****************************************
    // INNER CLASSES
    //****************************************

}
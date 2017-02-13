package de.hsmainz.pubapp.routing.web;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import de.hsmainz.pubapp.routing.controller.HttpAPIRequest;
import de.hsmainz.pubapp.routing.controller.HttpGraphhopperRequest;
import de.hsmainz.pubapp.routing.model.geojson.GeoJsonCollection;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * @author Sarah
 * @since 09.12.2016
 */
@Path("routing")
public class Routing extends RoutingTemplate {

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
    public String queryText(
            @QueryParam("startPoint") String startPoint,
            @QueryParam("endPoint") String endPoint,
            @QueryParam("locale") @DefaultValue("de") String locale,
            @QueryParam("pointsEncoded") @DefaultValue("false") String pointsEncoded,
            @QueryParam("callback") @DefaultValue("")  String callback){
        Gson gson = new Gson();

        if(!validateInput(startPoint, endPoint, locale, pointsEncoded)) {
            // TODO return proper errors
            JsonObject tempError = new JsonObject();
            tempError.addProperty("Error", "Some Error (TBD) occured ;)");
            return gson.toJson(tempError);
        }

        HttpAPIRequest httpApiRequest = new HttpGraphhopperRequest();
        GeoJsonCollection geoJsonColection = httpApiRequest.requestRouting(startPoint, endPoint, locale, pointsEncoded);
        return jsonCallbackWrapper(callback, gson.toJson(geoJsonColection));
    }

    //****************************************
    // PRIVATE METHODS
    //****************************************

    /**
     *
     * @param startPoint
     * @param endPoint
     * @param locale
     * @param pointsEncoded
     * @return
     */
    private boolean validateInput(String startPoint,
                                  String endPoint,
                                  String locale,
                                  String pointsEncoded) {

        // locale could be null/empty/something else, since graphhopper defaults to "en"
        // https://graphhopper.com/api/1/docs/routing/
        if (locale == null || locale.isEmpty()){
            System.out.println("locale null or empty");
            return false;
        }

//        if (inputJson.getLocale() != "de" || inputJson.getLocale() != "en" || inputJson.getLocale() != "fr" || inputJson.getLocale() != "it"){
//            System.out.println("locale not supported");
//            return false;
//        }

        if (startPoint == null || startPoint.isEmpty() || endPoint == null || endPoint.isEmpty()) {
            System.out.println("start- and/or endpoint null or empty");
            return false;
        }
        // TODO? validate if start- & endpoint are proper points?
        // TODO? validate pointsEncoded?

        return true;
    }

    //****************************************
    // INNER CLASSES
    //****************************************

}

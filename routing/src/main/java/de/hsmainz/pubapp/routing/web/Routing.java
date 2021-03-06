package de.hsmainz.pubapp.routing.web;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import de.hsmainz.pubapp.routing.controller.HttpGraphhopperRequest;
import de.hsmainz.pubapp.routing.model.geojson.GeoJsonCollection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author Sarah
 * @since 09.12.2016
 */
@Path("routing")
public class Routing extends RoutingTemplate {

    //****************************************
    // CONSTANTS
    //****************************************

    private final ResourceBundle labels = ResourceBundle.getBundle("labels", Locale.getDefault());
    private static final Logger logger = LogManager.getLogger(Routing.class);

    //****************************************
    // VARIABLES
    //****************************************

    private String errorMessage;

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
     * @param startPoint Start point for the route
     * @param endPoint End point for the route
     * @param locale Locale or language
     * @param vehicle Measure of transportation
     * @param pointsEncoded Should graphhoper proprietary encode the points
     * @param callback Callback name for JSONP
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String queryText(
            @QueryParam("startPoint") String startPoint,
            @QueryParam("endPoint") String endPoint,
            @QueryParam("locale") @DefaultValue("de") String locale,
            @QueryParam("vehicle") @DefaultValue("foot") String vehicle,
            @QueryParam("pointsEncoded") @DefaultValue("false") String pointsEncoded,
            @QueryParam("callback") @DefaultValue("") String callback){
        Gson gson = new Gson();

        if(!validateInput(startPoint, endPoint, locale, vehicle, pointsEncoded)) {
            return jsonCallbackWrapper(callback, getErrorResponse(errorMessage));
        }

        HttpGraphhopperRequest httpGraphhopperRequest = new HttpGraphhopperRequest();
        GeoJsonCollection geoJsonCollection = httpGraphhopperRequest.requestRouting(startPoint, endPoint, locale, vehicle, pointsEncoded);

        if ("{}".equals(gson.toJson(geoJsonCollection))) { // empty
            return jsonCallbackWrapper(callback, getErrorResponse(labels.getString("message_graphhopper_req_failed")));
        }

        return jsonCallbackWrapper(callback, gson.toJson(geoJsonCollection));
    }

    //****************************************
    // PRIVATE METHODS
    //****************************************

    /**
     * Validating the queryText
     *
     * @param startPoint Start point for the route
     * @param endPoint End point for the route
     * @param locale Locale or language
     * @param vehicle Measure of transportation
     * @param pointsEncoded Should graphhoper proprietary encode the points
     * @return If valid true, else false
     */
    private boolean validateInput(String startPoint,
                                  String endPoint,
                                  String locale,
                                  String vehicle,
                                  String pointsEncoded) {
        boolean valid = true;

        // locale could actually be null/empty/something else, since graphhopper defaults to "en"
        // https://graphhopper.com/api/1/docs/routing/
        if (locale == null || locale.isEmpty()) {
            errorMessage = labels.getString("error_no_locale");
        } else if (!locale.matches("de|en|fr|it")) {
            errorMessage = labels.getString("error_locale_not_supported");
        }

        if (vehicle == null || vehicle.isEmpty()) {
            errorMessage = labels.getString("error_no_vehicle");
        } else if (!vehicle.matches("foot|car")) {
            errorMessage = labels.getString("error_vehicle_not_supported");
        }

        // Future? validate if start- & endpoint are proper points
        // e.g. using regex for twice, separated by ",",
        // one number or two numbers, optional followed by "." and at least one number
        if (startPoint == null || startPoint.isEmpty()) {
            errorMessage = labels.getString("error_no_startpoint");
        }

        if (endPoint == null || endPoint.isEmpty()) {
            errorMessage = labels.getString("error_no_endpoint");
        }

        if (pointsEncoded == null || pointsEncoded.isEmpty() || !pointsEncoded.matches("true|false")) {
            // pointsEncoded could be defined as boolean, but since it will be send via HTTP anyway…
            errorMessage = labels.getString("error_pointsencoded_invalid");
        }

        if (!(errorMessage == null || errorMessage.isEmpty())) {
            valid = false;
        }

        return valid;
    }

    /**
     * Get the error message by the key
     *
     * @param errorMessage Error message key
     * @return Returns stringified json like {"Error": "some error"}
     */
    private String getErrorResponse(String errorMessage) {
        JsonObject error = new JsonObject();
        error.addProperty("Error", errorMessage);
        logger.info("Error message returned to Client: " + errorMessage);
        return new Gson().toJson(error);
    }

    //****************************************
    // INNER CLASSES
    //****************************************

}

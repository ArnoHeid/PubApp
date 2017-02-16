package de.hsmainz.pubapp.geocoder.controller;

import com.google.gson.Gson;
import de.hsmainz.pubapp.geocoder.model.ClientInputJson;
import de.hsmainz.pubapp.geocoder.model.ErrorJson;
import de.hsmainz.pubapp.geocoder.model.geojson.GeoJsonCollection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URI;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Interface for all different geocoder APIs
 *
 * @author Arno
 * @since 15.12.2016
 */
public abstract class HttpAPIRequest {

    //****************************************
    // CONSTANTS
    //****************************************

    static final ResourceBundle lables = ResourceBundle.getBundle("lable", Locale.getDefault());
    static final Logger logger = LogManager.getLogger(HttpGraphhopperRequest.class);

    //****************************************
    // VARIABLES
    //****************************************

    Gson gson = new Gson();

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
     * Executes request to geocoder API and creates GeoJSON. Custom ClientJson is used for the input
     *
     * @param inputJson the request parameters combined in a custom ClientJson
     * @return API response converted to a String
     */
    public abstract String requestGeocoder(ClientInputJson inputJson);

    /**
     * Executes request to geocoder API and creates GeoJSON
     *
     * @param queryString the string containing the address
     * @param locale      the string defining the used language
     * @return API response converted to a String
     */
    public abstract String requestGeocoder(String queryString, String locale);

    //****************************************
    // PRIVATE METHODS
    //****************************************

    /**
     * Executes the request to the API
     *
     * @param uri the geocoder URL
     * @return the requested geoJSON
     * @throws throws an exception if the request fails
     */
    abstract GeoJsonCollection doHttpGet(URI uri) throws IOException;

    /**
     * Method to catch exceptions and create ErrorJSONs
     *
     * @param uri
     * @return returns the GeoJSON or ErrorJSON as a String
     */
    String request(URI uri) {
        String returnString;
        try {
            GeoJsonCollection geoJsonCollection = doHttpGet(uri);
            if (validateOutput(geoJsonCollection)) {
                returnString = gson.toJson(geoJsonCollection);
            } else {
                returnString = gson.toJson(new ErrorJson(lables.getString("message_no_location")));
            }
        } catch (IOException e) {
            logger.catching(e);
            returnString = gson.toJson(new ErrorJson(lables.getString("error_API_request_Faild")));
        }
        return returnString;
    }

    /**
     * validates the Input to reduce unnecessary request to API
     *
     * @param inputJson the InputJSON to be validated
     * @return returns true if InputJSON is valid
     */
    boolean validateInput(ClientInputJson inputJson) {
        boolean returnValue = true;
        if (inputJson.getQueryString() == null || inputJson.getQueryString().isEmpty()) {
            returnValue = false;
        }
        if (inputJson.getLocale() == null || inputJson.getLocale().isEmpty()) {
            returnValue = false;
        }
        return returnValue;
    }

    /**
     * validates the Input to reduce unnecessary request to API
     *
     * @param inputString the Input String to be validated
     * @return true if Input String is not Empty
     */
    boolean validateInput(String inputString) {
        boolean returnValue = true;
        if (inputString == null || inputString.isEmpty()) {
            returnValue = false;
        }
        return returnValue;
    }

    /**
     * validates the output from the API
     *
     * @param geoJsonCollection the API outputJSON to be validated
     * @return returns true if the outputJSON is not empty
     */
    private boolean validateOutput(GeoJsonCollection geoJsonCollection) {
        return !geoJsonCollection.getFeatures().isEmpty();
    }
    //****************************************
    // INNER CLASSES
    //****************************************
}

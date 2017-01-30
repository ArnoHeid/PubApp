package de.hsmainz.pubapp.geocoder.httpapirequest;

import de.hsmainz.pubapp.geocoder.jsonparser.ClientInputJson;
import de.hsmainz.pubapp.geocoder.jsonparser.geojson.GeoJsonCollection;

/**
 * Interface for all different geocoder APIs
 *
 * @author Arno
 * @since 15.12.2016
 */
public interface HttpAPIRequest {

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
     * Executes request to geocoder API and creates GeoJSON. Custom ClientJson is used for the input
     *
     * @param inputJson the request parameters combined in a custom ClientJson
     * @return API response converted to GeoJSON
     */
    GeoJsonCollection requestGeocoder(ClientInputJson inputJson);

    /**
     * Executes request to geocoder API and creates GeoJSON
     *
     * @param queryString the string containing the address
     * @param locale      the string defining the used language
     * @return API response converted to GeoJSON
     */
    GeoJsonCollection requestGeocoder(String queryString, String locale);

    //****************************************
    // PRIVATE METHODS
    //****************************************

    //****************************************
    // INNER CLASSES
    //****************************************
}

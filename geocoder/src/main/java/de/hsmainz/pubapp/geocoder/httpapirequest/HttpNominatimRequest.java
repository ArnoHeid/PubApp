package de.hsmainz.pubapp.geocoder.httpapirequest;


import de.hsmainz.pubapp.geocoder.jsonparser.ClientInputJson;

/**
 * @author Arno
 * @since 03.02.2017.
 */
public class HttpNominatimRequest implements HttpAPIRequest {

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

    @Override
    public String requestGeocoder(ClientInputJson inputJson) {
        return null;
    }

    @Override
    public String requestGeocoder(String queryString, String locale) {
        return null;
    }

    //****************************************
    // PRIVATE METHODS
    //****************************************

    //****************************************
    // INNER CLASSES
    //****************************************

    //http://nominatim.openstreetmap.org/search?q=Berlin&format=json&addressdetails=1&limit=10

}

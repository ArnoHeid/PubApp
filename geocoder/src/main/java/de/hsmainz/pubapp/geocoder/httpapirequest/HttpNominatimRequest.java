package de.hsmainz.pubapp.geocoder.httpapirequest;


import com.google.gson.Gson;
import de.hsmainz.pubapp.geocoder.jsonparser.ClientInputJson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Arno
 * @since 03.02.2017.
 */
public class HttpNominatimRequest implements HttpAPIRequest {

    //****************************************
    // CONSTANTS
    //****************************************

    private static final Logger logger = LogManager.getLogger(HttpGraphhopperRequest.class);

    //****************************************
    // VARIABLES
    //****************************************

    private Gson gson = new Gson();

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

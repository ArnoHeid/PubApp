package de.hsmainz.pubapp.geocoder.resource;

/**
 * father class for all resources
 *
 * @author Arno
 * @since 16.12.2016.
 */
public class ResourceTemplate {

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
     * wraps JSON string with callback string
     *
     * @param callback
     * @param json
     * @return the wrapped callback string
     */
    protected String jsonCallbackWraper(String callback, String json) {
        if (callback.isEmpty() || callback == null)
            return json;
        else
            return callback + '(' + json + ')';
    }

    //****************************************
    // PRIVATE METHODS
    //****************************************

    //****************************************
    // INNER CLASSES
    //****************************************

}

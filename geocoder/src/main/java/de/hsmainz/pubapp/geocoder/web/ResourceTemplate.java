package de.hsmainz.pubapp.geocoder.web;

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

    //****************************************
    // PRIVATE METHODS
    //****************************************

    /**
     * wraps JSON string with callback string
     *
     * @param callback the callback string
     * @param json the json wrapped with the callback string
     * @return the wrapped callback string
     */
    protected String jsonCallbackWraper(String callback, String json) {
        if (callback == null || callback.isEmpty())
            return json;
        else
            return callback + '(' + json + ')';
    }

    //****************************************
    // INNER CLASSES
    //****************************************

}

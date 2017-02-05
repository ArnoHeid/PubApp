package de.hsmainz.pubapp.routing.resource;

/**
 * @author Sarah
 * @since 17.12.2016
 */
public class RoutingTemplate {

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
     * Wraps JSON string with callback string
     *
     * @param callback the callback string
     * @param json the json wrapped with the callback string
     * @return the wrapped callback string
     */
    protected String jsonCallbackWrapper(String callback, String json){
        if(callback == null || callback.isEmpty()) {
            return json;
        }
        // … else return json wrapped in callback
        return callback + '(' + json + ')';
    }

    //****************************************
    // INNER CLASSES
    //****************************************

}

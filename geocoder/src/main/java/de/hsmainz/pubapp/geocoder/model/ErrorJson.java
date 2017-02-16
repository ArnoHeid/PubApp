package de.hsmainz.pubapp.geocoder.model;

/**
 * JSON to report an error to the client
 *
 * @author Arno
 * @since 31.01.2017.
 */
public class ErrorJson {

    //****************************************
    // CONSTANTS
    //****************************************

    //****************************************
    // VARIABLES
    //****************************************
    private String type = "Error";
    private String errortext;
    //****************************************
    // INIT/CONSTRUCTOR
    //****************************************

    public ErrorJson() {
        errortext = "Default Error Text";
    }

    public ErrorJson(String errorText) {
        this.errortext = errorText;
    }

    //****************************************
    // GETTER/SETTER
    //****************************************

    //****************************************
    // PUBLIC METHODS
    //****************************************

    //****************************************
    // PRIVATE METHODS
    //****************************************

    //****************************************
    // INNER CLASSES
    //****************************************

}

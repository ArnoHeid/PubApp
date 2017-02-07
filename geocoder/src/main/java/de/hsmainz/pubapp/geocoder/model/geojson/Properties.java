package de.hsmainz.pubapp.geocoder.model.geojson;

/**
 * Additional information to expand the coordinate information
 *
 * @author Arno
 * @since 07.12.2016.
 */
public class Properties {

    //****************************************
    // CONSTANTS
    //****************************************

    //****************************************
    // VARIABLES
    //****************************************

    private String name;
    private String country;

    //****************************************
    // INIT/CONSTRUCTOR
    //****************************************

    public Properties(String country, String name) {
        this.name = name;
        this.country = country;
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

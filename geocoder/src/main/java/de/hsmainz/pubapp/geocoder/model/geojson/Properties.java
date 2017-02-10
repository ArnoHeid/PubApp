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
    private String licence;

    //****************************************
    // INIT/CONSTRUCTOR
    //****************************************

    public Properties(String country, String name, String licence) {
        this.name = name;
        this.country = country;
        this.licence = licence;
    }

    //****************************************
    // GETTER/SETTER
    //****************************************

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public String getLicence() {
        return licence;
    }

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

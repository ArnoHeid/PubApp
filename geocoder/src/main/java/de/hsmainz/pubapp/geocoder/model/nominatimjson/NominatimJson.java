package de.hsmainz.pubapp.geocoder.model.nominatimjson;

/**
 * Main nominatimJSON
 *
 * @author Arno
 * @since 03.02.2017.
 */
public class NominatimJson {


    //****************************************
    // CONSTANTS
    //****************************************

    //****************************************
    // VARIABLES
    //****************************************

    private String display_name;
    private double lat;
    private double lon;
    private Address address;
    private String licence;

    //****************************************
    // INIT/CONSTRUCTOR
    //****************************************

    //****************************************
    // GETTER/SETTER
    //****************************************

    public String getDisplay_name() {
        return display_name;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public Address getAdress() {
        return address;
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

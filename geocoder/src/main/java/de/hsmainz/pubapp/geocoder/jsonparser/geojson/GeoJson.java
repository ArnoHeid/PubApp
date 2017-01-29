package de.hsmainz.pubapp.geocoder.jsonparser.geojson;

import de.hsmainz.pubapp.geocoder.jsonparser.graphhopperjson.HitsJson;

/**
 * GeoJSON with Type "Feature"
 *
 * @author Arno
 * @since 07.12.2016.
 */
public class GeoJson {

    //****************************************
    // CONSTANTS
    //****************************************

    //****************************************
    // VARIABLES
    //****************************************

    private String type;
    private Properties properties;
    private Geometry geometry;

    //****************************************
    // INIT/CONSTRUCTOR
    //****************************************

    public GeoJson(HitsJson hitsJson) {
        type = "Feature";
        properties = new Properties(hitsJson.getCountry(), hitsJson.getName());
        geometry = new Geometry(hitsJson.getPoint());

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

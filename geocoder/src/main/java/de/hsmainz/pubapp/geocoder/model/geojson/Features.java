package de.hsmainz.pubapp.geocoder.model.geojson;

import de.hsmainz.pubapp.geocoder.model.graphhopperjson.HitsJson;
import de.hsmainz.pubapp.geocoder.model.nominatimjson.NominatimJson;

/**
 * GeoJSON with Type "Feature"
 *
 * @author Arno
 * @since 07.12.2016.
 */
public class Features {

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

    public Features(HitsJson hitsJson) {
        type = "Feature";
        properties = new Properties(hitsJson.getCountry(), hitsJson.getName());
        geometry = new Geometry(hitsJson.getPoint());

    }

    public Features(NominatimJson nomJson) {
        type = "Feature";
        properties = new Properties(nomJson.getAdress().getCountry(), nomJson.getDisplay_name());
        geometry = new Geometry(nomJson.getLat(),nomJson.getLon());
    }

    //****************************************
    // GETTER/SETTER
    //****************************************

    public String getType() {
        return type;
    }

    public Properties getProperties() {
        return properties;
    }

    public Geometry getGeometry() {
        return geometry;
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

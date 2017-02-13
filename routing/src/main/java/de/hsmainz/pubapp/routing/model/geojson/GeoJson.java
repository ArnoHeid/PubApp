package de.hsmainz.pubapp.routing.model.geojson;

import com.google.gson.JsonObject;
import de.hsmainz.pubapp.routing.model.graphhopperjson.PathsJson;

/**
 * @author Sarah
 * @since 20.12.2016
 */
public class GeoJson {

    //****************************************
    // CONSTANTS
    //****************************************

    //****************************************
    // VARIABLES
    //****************************************

    private String type;
    private JsonObject geometry;
    private Properties properties = new Properties(); // GeoJSON requires `properties` to exist, but is empty atm.

    //****************************************
    // INIT/CONSTRUCTOR
    //****************************************

    public GeoJson(PathsJson pathsJson) {
        type = "Feature";
        geometry = pathsJson.getPoints();
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

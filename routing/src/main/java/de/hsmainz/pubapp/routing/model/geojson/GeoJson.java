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
    private Properties properties = new Properties();

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

    public String getType() {
        return type;
    }

    public JsonObject getGeometry() {
        return geometry;
    }

    public Properties getProperties() {
        return properties;
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

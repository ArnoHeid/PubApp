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

    private String type; //NOSONAR this must exist for the GeoJSON, but isnt used here, which sonarcube doesnt like
    private JsonObject geometry; //NOSONAR this must exist for the GeoJSON, but isnt used here, which sonarcube doesnt like
    private Properties properties = new Properties(); //NOSONAR this must exist for the GeoJSON, but isnt used here, which sonarcube doesnt like

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

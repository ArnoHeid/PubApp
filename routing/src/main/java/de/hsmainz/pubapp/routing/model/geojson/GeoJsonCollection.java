package de.hsmainz.pubapp.routing.model.geojson;

import de.hsmainz.pubapp.routing.model.graphhopperjson.GraphhopperJson;
import de.hsmainz.pubapp.routing.model.graphhopperjson.PathsJson;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Sarah
 * @since 20.12.2016
 */
public class GeoJsonCollection {

    //****************************************
    // CONSTANTS
    //****************************************

    //****************************************
    // VARIABLES
    //****************************************

    private String type; //NOSONAR this must exist for the GeoJSON, but isnt used here, which sonarcube doesnt like
    private List<GeoJson> features;

    //****************************************
    // INIT/CONSTRUCTOR
    //****************************************

    public GeoJsonCollection() {
        // empty to be able to return empty collection
    }

    public GeoJsonCollection(GraphhopperJson graphhopperJson){
        type = "FeatureCollection";
        features = new ArrayList<>();
        for (PathsJson pathsJson: graphhopperJson.getPaths()) {
            features.add(new GeoJson(pathsJson));
        }
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

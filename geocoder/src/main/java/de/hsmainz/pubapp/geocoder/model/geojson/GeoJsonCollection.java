package de.hsmainz.pubapp.geocoder.model.geojson;

import de.hsmainz.pubapp.geocoder.model.graphhopperjson.GrahhopperJson;
import de.hsmainz.pubapp.geocoder.model.graphhopperjson.HitsJson;
import de.hsmainz.pubapp.geocoder.model.nominatimjson.NominatimJson;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to combine GeoJSONs to a GeoJSON collection
 *
 * @author Arno
 * @since 07.12.2016.
 */
public class GeoJsonCollection {

    //****************************************
    // CONSTANTS
    //****************************************

    //****************************************
    // VARIABLES
    //****************************************

    private String type;
    private List<Features> features;

    //****************************************
    // INIT/CONSTRUCTOR
    //****************************************

    public GeoJsonCollection(GrahhopperJson grahhopperJson) {
        type = "FeatureCollection";
        features = new ArrayList<>();
        for (HitsJson hitsJson : grahhopperJson.getHits()) {
            features.add(new Features(hitsJson));
        }
    }

    public GeoJsonCollection(List<NominatimJson> nominatimJson) {
        type = "FeatureCollection";
        features = new ArrayList<>();
        for (NominatimJson nomJson : nominatimJson) {
            features.add(new Features(nomJson));
        }
    }

    //****************************************
    // GETTER/SETTER
    //****************************************

    public List<Features> getFeatures() {
        return features;
    }

    public String getType() {
        return type;
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

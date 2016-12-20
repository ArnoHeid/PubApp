package de.hsmainz.pubApp.routing.jsonparser.geoJson;

import de.hsmainz.pubApp.routing.jsonparser.graphhopperJson.GraphhopperJson;
import de.hsmainz.pubApp.routing.jsonparser.graphhopperJson.PathsJson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sarah on 20.12.2016.
 */
public class GeoJsonCollection {
    private String type;
    private List<GeoJson> features;

    public GeoJsonCollection() {

    }

    public GeoJsonCollection(GraphhopperJson graphhopperJson){
        type = "FeatureCollection";
        features = new ArrayList<>();
        for (PathsJson pathsJson: graphhopperJson.getPaths()) {
            features.add(new GeoJson(pathsJson));
        }
    }
}

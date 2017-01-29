package de.hsmainz.pubapp.geocoder.jsonparser.geojson;

import de.hsmainz.pubapp.geocoder.jsonparser.graphhopperjson.GrahhopperJson;
import de.hsmainz.pubapp.geocoder.jsonparser.graphhopperjson.HitsJson;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to combine GeoJSONs to a GeoJSON collection
 *
 * @author Arno
 * @since 07.12.2016.
 */
public class GeoJsonColection {
    private String type;
    private List<GeoJson> features;


    public GeoJsonColection(GrahhopperJson grahhopperJson) {
        type = "FeatureCollection";
        features = new ArrayList<>();
        for (HitsJson hitsJson : grahhopperJson.getHits()) {
            features.add(new GeoJson(hitsJson));
        }

    }

    public GeoJsonColection() {
        //default Constructor for use this fail request
    }
}

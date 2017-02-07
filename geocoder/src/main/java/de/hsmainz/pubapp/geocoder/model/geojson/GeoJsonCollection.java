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
    private String type;
    private List<GeoJson> features;


    public GeoJsonCollection(GrahhopperJson grahhopperJson) {
        type = "FeatureCollection";
        features = new ArrayList<>();
        for (HitsJson hitsJson : grahhopperJson.getHits()) {
            features.add(new GeoJson(hitsJson));
        }

    }

    public GeoJsonCollection() {
        //default Constructor for use this fail request
    }

    public GeoJsonCollection(List<NominatimJson> nominatimJson) {
        type = "FeatureCollection";
        features = new ArrayList<>();
        for (NominatimJson nomJson : nominatimJson){
            features.add(new GeoJson(nomJson));
        }

    }

}

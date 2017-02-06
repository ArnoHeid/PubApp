package de.hsmainz.pubapp.geocoder.jsonparser.geojson;

import de.hsmainz.pubapp.geocoder.jsonparser.graphhopperjson.GrahhopperJson;
import de.hsmainz.pubapp.geocoder.jsonparser.graphhopperjson.HitsJson;
import de.hsmainz.pubapp.geocoder.jsonparser.nominatimjson.NominatimJson;

import java.util.ArrayList;
import java.util.Collection;
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
    }

    public GeoJsonCollection(Collection<NominatimJson> nominatimJson) {
    }
}

package de.hsmainz.pubapp.geocoder.jsonparser.geoJson;

import de.hsmainz.pubapp.geocoder.jsonparser.graphhopperJson.GrahhopperJson;
import de.hsmainz.pubapp.geocoder.jsonparser.graphhopperJson.HitsJson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Arno on 07.12.2016.
 */
public class GeoJsonColection {
    private String type;
    private List<GeoJson>  features;
    
    
    public GeoJsonColection(GrahhopperJson grahhopperJson){
        type = "FeatureCollection";
        features = new ArrayList<>();
        for (HitsJson hitsJson: grahhopperJson.getHits())
        {
            features.add(new GeoJson(hitsJson));
        }
        
    }


    public GeoJsonColection() {

    }
}

package de.hs_mainz.pubApp.jsonparser.geoJson;

import de.hs_mainz.pubApp.jsonparser.graphhopperJson.GrahhopperJson;
import de.hs_mainz.pubApp.jsonparser.graphhopperJson.HitsJson;

import java.util.List;

/**
 * Created by Arno on 07.12.2016.
 */
public class GeoJsonColection {
    private String type;
    private List<GeoJson>  features;
    
    
    public GeoJsonColection(GrahhopperJson grahhopperJson){
        type = "FeatureCollection";

        for (HitsJson hitsJson: grahhopperJson.getHits())
        {
            features.add(new GeoJson(hitsJson));
        }
        
    }
    
    
}

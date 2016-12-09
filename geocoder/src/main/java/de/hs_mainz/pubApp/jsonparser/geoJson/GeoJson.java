package de.hs_mainz.pubApp.jsonparser.geoJson;

import de.hs_mainz.pubApp.jsonparser.graphhopperJson.GrahhopperJson;
import de.hs_mainz.pubApp.jsonparser.graphhopperJson.HitsJson;

/**
 * Created by Arno on 07.12.2016.
 */
public class GeoJson {
    private String type;
    private Properties properties;
    private Geometry geometry;

    public GeoJson(HitsJson hitsJson) {
        type = "Feature";
        properties = new Properties(hitsJson.getCountry(),hitsJson.getName());
        geometry = new Geometry(hitsJson.getPoint());

    }
}

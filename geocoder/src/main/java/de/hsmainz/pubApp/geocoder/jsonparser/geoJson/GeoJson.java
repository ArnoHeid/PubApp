package de.hsmainz.pubApp.geocoder.jsonparser.geoJson;

import de.hsmainz.pubApp.geocoder.jsonparser.graphhopperJson.HitsJson;

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

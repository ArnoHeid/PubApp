package de.hsmainz.pubApp.routing.jsonparser.geoJson;

import com.google.gson.JsonObject;
import de.hsmainz.pubApp.routing.jsonparser.graphhopperJson.PathsJson;

/**
 * Created by Sarah on 20.12.2016.
 */
public class GeoJson {
    private String type;
    private JsonObject geometry;
    private Properties properties = new Properties(); // GeoJSON requires `properties` to exist, but is empty atm.

    public GeoJson(PathsJson pathsJson) {
        type = "Feature";
        geometry = pathsJson.getPoints();

    }
}

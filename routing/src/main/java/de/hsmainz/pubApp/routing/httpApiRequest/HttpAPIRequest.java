package de.hsmainz.pubApp.routing.httpApiRequest;

import de.hsmainz.pubApp.routing.jsonparser.geoJson.GeoJsonCollection;

/**
 * Created by Sarah on 20.12.2016.
 */
public interface HttpAPIRequest {
    GeoJsonCollection requestRouting(String startPoint,
                                     String endPoint,
                                     String locale,
                                     String pointsEncoded);
}

package de.hsmainz.pubapp.geocoder.httpApiRequest;

import de.hsmainz.pubapp.geocoder.jsonparser.ClientInputJson;
import de.hsmainz.pubapp.geocoder.jsonparser.geoJson.GeoJsonColection;

/**
 * Created by Arno on 15.12.2016.
 */
public interface HttpAPIRequest {
    GeoJsonColection requestGeocoder(ClientInputJson inputJson);

    GeoJsonColection requestGeocoder(String queryString, String locale);
}

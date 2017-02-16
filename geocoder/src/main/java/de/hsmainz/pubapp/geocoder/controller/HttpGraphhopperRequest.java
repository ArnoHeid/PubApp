package de.hsmainz.pubapp.geocoder.controller;

import de.hsmainz.pubapp.geocoder.MyProperties;
import de.hsmainz.pubapp.geocoder.model.ClientInputJson;
import de.hsmainz.pubapp.geocoder.model.ErrorJson;
import de.hsmainz.pubapp.geocoder.model.geojson.GeoJsonCollection;
import de.hsmainz.pubapp.geocoder.model.graphhopperjson.GrahhopperJson;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Class for using Graphhopper as Geocoder
 *
 * @author Arno
 * @since 04.12.2016
 */
public class HttpGraphhopperRequest extends HttpAPIRequest {

    //****************************************
    // CONSTANTS
    //****************************************

    //****************************************
    // VARIABLES
    //****************************************

    //****************************************
    // INIT/CONSTRUCTOR
    //****************************************

    //****************************************
    // GETTER/SETTER
    //****************************************

    //****************************************
    // PUBLIC METHODS
    //****************************************

    /**
     * Executes request to graphhopper-geocoder API and creates a GeoJSON. Custom ClientJson is used for the input
     *
     * @param inputJson the request parameters combined in a custom ClientJson
     * @return graphhopper-API response converted to a String
     */
    @Override
    public String requestGeocoder(ClientInputJson inputJson) {
        String returnString;
        if (!validateInput(inputJson)) {
            returnString = gson.toJson(new ErrorJson(lables.getString("message_Input_Empty")));
        } else {
            try {
                URI uri = buildGraphhopperUri(inputJson);
                returnString = request(uri);
            } catch (URISyntaxException e) {
                logger.catching(e);
                returnString = gson.toJson(new ErrorJson(lables.getString("error_incorrect_URI")));
            }
        }
        return returnString;
    }

    /**
     * Executes request to graphhopper-geocoder API and creates a GeoJSON
     *
     * @param queryString the string containing the address
     * @param locale      the string defining the used language
     * @return graphhopper-API response converted to a String
     */
    @Override
    public String requestGeocoder(String queryString, String locale) {
        String returnString;
        if (!validateInput(queryString)) {
            returnString = gson.toJson(new ErrorJson(lables.getString("message_Input_Empty")));
        } else {
            try {
                URI uri = buildGraphhopperUri(queryString, locale);
                returnString = request(uri);
            } catch (URISyntaxException e) {
                logger.catching(e);
                returnString = gson.toJson(new ErrorJson(lables.getString("error_incorrect_URI")));
            }
        }
        return returnString;
    }

    //****************************************
    // PRIVATE METHODS
    //****************************************

    /**
     * Executes the request to the API
     *
     * @param uri the URL for the request to geocoder
     * @return the requested geoJSON
     * @throws throws an exception if the request fails
     */
    @Override
    GeoJsonCollection doHttpGet(URI uri) throws IOException {
        GrahhopperJson grahhopperJson;
        GeoJsonCollection geoJsonCollection;
        HttpGet httpget = new HttpGet(uri);
        try (CloseableHttpClient httpclient = HttpClients.createDefault(); CloseableHttpResponse response = httpclient.execute(httpget)) {
            InputStream inputStream = response.getEntity().getContent();
            Reader reader = new InputStreamReader(inputStream, "UTF-8");
            grahhopperJson = gson.fromJson(reader, GrahhopperJson.class);
            geoJsonCollection = new GeoJsonCollection(grahhopperJson);
            inputStream.close();
        }
        return geoJsonCollection;
    }

    /**
     * Creates the URI for graphhopper API request
     *
     * @param inputJson Class with input parameters
     * @return Uri for geocoder request to graphhopper API
     */
    private URI buildGraphhopperUri(ClientInputJson inputJson) throws URISyntaxException {
        return buildGraphhopperUri(inputJson.getQueryString(), inputJson.getLocale());
    }

    /**
     * Creates the URI for graphhopper API request
     *
     * @param queryString the string containing the address
     * @param locale      the string defining the used language
     * @return Uri for geocoder request to graphhopper API
     */
    private URI buildGraphhopperUri(String queryString, String locale) throws URISyntaxException {
        URIBuilder uriBuilder = new URIBuilder();
        uriBuilder.setScheme(MyProperties.getInstance().getProperty("geo_gscheme"));
        uriBuilder.setHost(MyProperties.getInstance().getProperty("geo_ghost"));
        uriBuilder.setPath(MyProperties.getInstance().getProperty("geo_gpath"));
        uriBuilder.setParameter("q", queryString);
        uriBuilder.setParameter("locale", locale);
        uriBuilder.setParameter("key", MyProperties.getInstance().getProperty("graphhopper_key"));
        return uriBuilder.build();
    }

    //****************************************
    // INNER CLASSES
    //****************************************
}

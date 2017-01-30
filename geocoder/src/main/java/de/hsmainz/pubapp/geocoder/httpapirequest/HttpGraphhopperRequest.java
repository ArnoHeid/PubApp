package de.hsmainz.pubapp.geocoder.httpapirequest;

import com.google.gson.Gson;
import de.hsmainz.pubapp.geocoder.jsonparser.APIKeys;
import de.hsmainz.pubapp.geocoder.jsonparser.geojson.GeoJsonCollection;
import de.hsmainz.pubapp.geocoder.jsonparser.graphhopperjson.GrahhopperJson;
import de.hsmainz.pubapp.geocoder.jsonparser.ClientInputJson;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Class for using Graphhopper as Geocoder
 *
 * @author Arno
 * @since 04.12.2016
 */
public class HttpGraphhopperRequest implements HttpAPIRequest {

    //****************************************
    // CONSTANTS
    //****************************************

    private static final Logger logger = LogManager.getLogger(HttpGraphhopperRequest.class);

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
     * @return graphhopper-API response converted to GeoJSON
     */
    @Override
    public GeoJsonCollection requestGeocoder(ClientInputJson inputJson) {
        if (validateInput(inputJson))
            return new GeoJsonCollection();
        URI uri = buildGraphhopperUri(inputJson);
        return doHttpGet(uri);
    }

    /**
     * Executes request to graphhopper-geocoder API and creates a GeoJSON
     *
     * @param queryString the string containing the address
     * @param locale      the string defining the used language
     * @return graphhopper-API response converted to GeoJSON
     */
    @Override
    public GeoJsonCollection requestGeocoder(String queryString, String locale) {
        URI uri = buildGraphhopperUri(queryString, locale);
        return doHttpGet(uri);
    }

    //****************************************
    // PRIVATE METHODS
    //****************************************

    /**
     * Executes the request to the API
     *
     * @param uri the URL for the request to geocoder
     * @return the requested geoJSON
     */
    private GeoJsonCollection doHttpGet(URI uri) {
        Gson gson = new Gson();
        GrahhopperJson grahhopperJson;
        GeoJsonCollection geoJsonCollection;
        HttpGet httpget = new HttpGet(uri);
        try (CloseableHttpClient httpclient = HttpClients.createDefault(); CloseableHttpResponse response = httpclient.execute(httpget)) {
            InputStream inputStream = response.getEntity().getContent();
            Reader reader = new InputStreamReader(inputStream, "UTF-8");
            grahhopperJson = gson.fromJson(reader, GrahhopperJson.class);
            geoJsonCollection = new GeoJsonCollection(grahhopperJson);
            inputStream.close();
        } catch (IOException e) {
            logger.catching(e);
            geoJsonCollection = new GeoJsonCollection();
        }
        return geoJsonCollection;
        //TODO Better way to handle fail request
    }

    /**
     * Creates the URI for graphhopper API request
     *
     * @param inputJson Class with input parameters
     * @return Uri for geocoder request to graphhopper API
     */
    private URI buildGraphhopperUri(ClientInputJson inputJson) {
        return buildGraphhopperUri(inputJson.getQueryString(), inputJson.getLocale());
    }

    /**
     * Creates the URI for graphhopper API request
     *
     * @param queryString the string containing the address
     * @param locale      the string defining the used language
     * @return Uri for geocoder request to graphhopper API
     */
    private URI buildGraphhopperUri(String queryString, String locale) {
        APIKeys apiKeys;
        try {
            apiKeys = APIKeys.readKeys();
        } catch (FileNotFoundException e) {
            logger.catching(e);
            apiKeys = new APIKeys();
        }
        URIBuilder uriBuilder = new URIBuilder();
        uriBuilder.setScheme("http");
        uriBuilder.setHost("graphhopper.com");
        uriBuilder.setPath("/api/1/geocode");
        uriBuilder.setParameter("q", queryString);
        uriBuilder.setParameter("locale", locale);
        uriBuilder.setParameter("key", apiKeys.getGraphhopperKey());
        URI uri = null;
        try {
            uri = uriBuilder.build();
        } catch (URISyntaxException e) {
            logger.catching(e);
        }
        return uri;
    }

    /**
     * validates the Input to reduce unnecessary request to API
     *
     * @param inputJson the InputJSON to be validated
     * @return the true is InputJSON is valid
     */
    private boolean validateInput(ClientInputJson inputJson) {
        boolean returnValue = true;
        if (inputJson.getQueryString() == null || inputJson.getQueryString().isEmpty()) {
            returnValue = false;
        }
        if (inputJson.getLocale() == null || inputJson.getLocale().isEmpty()) {
            returnValue = false;
        }
        if ( inputJson.getLocale().matches("de|en|fr|it")) {
            returnValue = false;
        }
        return returnValue;
    }

    //TODO validateOutput(GrahhopperJson grahhopperJson)

    //****************************************
    // INNER CLASSES
    //****************************************
}

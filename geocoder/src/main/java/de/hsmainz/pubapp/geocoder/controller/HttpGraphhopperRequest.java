package de.hsmainz.pubapp.geocoder.controller;

import com.google.gson.Gson;
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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Locale;
import java.util.ResourceBundle;

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
    private static final ResourceBundle lables = ResourceBundle.getBundle("lable", Locale.getDefault());

    //****************************************
    // VARIABLES
    //****************************************

    private Gson gson = new Gson();

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
     * @return graphhopper-API response converted to GeoJSON
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

    private String request(URI uri) {
        String returnString;
        try {
            GeoJsonCollection geoJsonCollection = doHttpGet(uri);
            if (validateOutput(geoJsonCollection)) {
                returnString = gson.toJson(geoJsonCollection);
            } else {
                returnString = gson.toJson(new ErrorJson(lables.getString("message_no_location")));
            }
        } catch (IOException e) {
            e.printStackTrace();
            returnString = gson.toJson(new ErrorJson(lables.getString("error_API_request_Faild")));
        }
        return returnString;
    }

    /**
     * Executes the request to the API
     *
     * @param uri the URL for the request to geocoder
     * @return the requested geoJSON
     */
    private GeoJsonCollection doHttpGet(URI uri) throws IOException {
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

    /**
     * validates the Input to reduce unnecessary request to API
     *
     * @param inputJson the InputJSON to be validated
     * @return returns true if InputJSON is valid
     */
    private boolean validateInput(ClientInputJson inputJson) {
        boolean returnValue = true;
        if (inputJson.getQueryString() == null || inputJson.getQueryString().isEmpty()) {
            returnValue = false;
        }
        if (inputJson.getLocale() == null || inputJson.getLocale().isEmpty()) {
            returnValue = false;
        }
        if (inputJson.getLocale().matches("de|en|fr|it")) {
            returnValue = false;
        }
        return returnValue;
    }

    private boolean validateInput(String inputString) {
        boolean returnValue = true;
        if (inputString == null || inputString.isEmpty()) {
            returnValue = false;
        }
        return returnValue;
    }

    private boolean validateOutput(GeoJsonCollection geoJsonCollection) {
        return !geoJsonCollection.getFeatures().isEmpty();
    }

    //****************************************
    // INNER CLASSES
    //****************************************
}

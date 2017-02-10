package de.hsmainz.pubapp.geocoder.controller;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import de.hsmainz.pubapp.geocoder.MyProperties;
import de.hsmainz.pubapp.geocoder.model.ClientInputJson;
import de.hsmainz.pubapp.geocoder.model.ErrorJson;
import de.hsmainz.pubapp.geocoder.model.geojson.GeoJsonCollection;
import de.hsmainz.pubapp.geocoder.model.nominatimjson.NominatimJson;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.commons.io.IOUtils;


import java.io.*;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;


/**
 * Class for using Nominatim as Geocoder
 *
 * @author Arno
 * @since 03.02.2017.
 */
public class HttpNominatimRequest extends HttpAPIRequest {

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

    @Override
    public String requestGeocoder(ClientInputJson inputJson) {
        String returnString;
        if(!validateInput(inputJson)){
            returnString = gson.toJson(new ErrorJson(lables.getString("message_Input_Empty")));
        }else{
            try {
                URI uri = buildNominatimUri(inputJson);
                returnString = request(uri);
            } catch (URISyntaxException e) {
                logger.catching(e);
                returnString=gson.toJson(new ErrorJson(lables.getString("error_incorrect_URI")));
            }
        }

        return returnString;
    }

    @Override
    public String requestGeocoder(String queryString, String locale) {
        String returnString;
        if(!validateInput(queryString)){
            returnString = gson.toJson(new ErrorJson(lables.getString("message_Input_Empty")));
        }else{
            try {
                URI uri = buildNominatimUri(queryString,locale);
                returnString = request(uri);
            } catch (URISyntaxException e) {
                logger.catching(e);
                returnString=gson.toJson(new ErrorJson(lables.getString("error_incorrect_URI")));
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
     */
    @Override
    GeoJsonCollection doHttpGet(URI uri) throws IOException {
        List<NominatimJson> nominatimJson;
        GeoJsonCollection geoJsonCollection;
        HttpGet httpget = new HttpGet(uri);
        httpget.setHeader("Referrer",MyProperties.getInstance().getProperty("geo_referrer"));
        try (CloseableHttpClient httpclient = HttpClients.createDefault(); CloseableHttpResponse response = httpclient.execute(httpget)) {
            InputStream inputStream = response.getEntity().getContent();
            Reader reader = new InputStreamReader(inputStream, "UTF-8");
            String jsonString = IOUtils.toString(reader);
            jsonString = splitJsonString(jsonString);
            Type listType = new TypeToken<List<NominatimJson>>(){}.getType();
            nominatimJson = gson.fromJson(jsonString, listType);
            geoJsonCollection = new GeoJsonCollection(nominatimJson);
            inputStream.close();
        }
        return geoJsonCollection;
    }

    /**
     * Creates the URI for nominatim API request
     *
     * @param inputJson Class with input parameters
     * @return Uri for geocoder request to graphhopper API
     */
    private URI buildNominatimUri(ClientInputJson inputJson) throws URISyntaxException {
        return buildNominatimUri(inputJson.getQueryString(), inputJson.getLocale());
    }

    /**
     * Creates the URI for graphhopper API request
     *
     * @param queryString the string containing the address
     * @param locale      the string defining the used language
     * @return Uri for geocoder request to graphhopper API
     */
    private URI buildNominatimUri(String queryString, String locale) throws URISyntaxException {

        URIBuilder uriBuilder = new URIBuilder();
        uriBuilder.setScheme(MyProperties.getInstance().getProperty("geo_nscheme"));
        uriBuilder.setHost(MyProperties.getInstance().getProperty("geo_nhost"));
        uriBuilder.setPath(MyProperties.getInstance().getProperty("geo_npath"));
        uriBuilder.setParameter("q", queryString);
        uriBuilder.setParameter("format","json");
        uriBuilder.setParameter("json_callback","cb");
        uriBuilder.setParameter("addressdetails","1");
        uriBuilder.setParameter("limit","2");

        return uriBuilder.build();
    }

    private String splitJsonString(String json){
        String returnString;
        int pos = json.indexOf( ')' );
        returnString = pos >= 0 ? json.substring( 0, pos ) : json;
        pos = returnString.indexOf('(');
        returnString =  pos >= 0 ? returnString.substring( pos + ")".length() ) : "";

        return returnString;
    }

    //****************************************
    // INNER CLASSES
    //****************************************

}

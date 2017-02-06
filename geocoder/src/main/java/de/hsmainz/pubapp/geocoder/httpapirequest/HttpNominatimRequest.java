package de.hsmainz.pubapp.geocoder.httpapirequest;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import de.hsmainz.pubapp.geocoder.MyProperties;
import de.hsmainz.pubapp.geocoder.jsonparser.ClientInputJson;
import de.hsmainz.pubapp.geocoder.jsonparser.geojson.GeoJsonCollection;
import de.hsmainz.pubapp.geocoder.jsonparser.nominatimjson.NominatimJson;
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
import java.util.Collection;


/**
 * @author Arno
 * @since 03.02.2017.
 */
public class HttpNominatimRequest implements HttpAPIRequest {

    //****************************************
    // CONSTANTS
    //****************************************

    private static final Logger logger = LogManager.getLogger(HttpGraphhopperRequest.class);

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

    @Override
    public String requestGeocoder(ClientInputJson inputJson) {
        return null;
    }

    @Override
    public String requestGeocoder(String queryString, String locale) {
        String returnString;

            URI uri = buildNominatimUri(queryString, locale);
            returnString = gson.toJson(doHttpGet(uri));

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
    private GeoJsonCollection doHttpGet(URI uri) {
        Gson gson = new Gson();
        Collection<NominatimJson> nominatimJson;
        GeoJsonCollection geoJsonCollection;
        HttpGet httpget = new HttpGet(uri);
        httpget.setHeader("Referrer","www.hs-mainz.de");
        try (CloseableHttpClient httpclient = HttpClients.createDefault(); CloseableHttpResponse response = httpclient.execute(httpget)) {
            InputStream inputStream = response.getEntity().getContent();
            Reader reader = new InputStreamReader(inputStream, "UTF-8");

            String jsonString = IOUtils.toString(reader);

            jsonString = splitJsonString(jsonString);

            Type listType = new TypeToken<Collection<NominatimJson>>(){}.getType();

            nominatimJson = gson.fromJson(jsonString, listType);

            geoJsonCollection = new GeoJsonCollection(nominatimJson);
            inputStream.close();
        } catch (IOException e) {
            logger.catching(e);
            geoJsonCollection = new GeoJsonCollection();
        }
        return geoJsonCollection;
        //TODO Better way to handle fail request
    }

    /**
     * Creates the URI for nominatim API request
     *
     * @param inputJson Class with input parameters
     * @return Uri for geocoder request to graphhopper API
     */
    private URI buildNoiminatimUri(ClientInputJson inputJson) {
        return buildNominatimUri(inputJson.getQueryString(), inputJson.getLocale());
    }

    /**
     * Creates the URI for graphhopper API request
     *
     * @param queryString the string containing the address
     * @param locale      the string defining the used language
     * @return Uri for geocoder request to graphhopper API
     */
    private URI buildNominatimUri(String queryString, String locale) {

        URIBuilder uriBuilder = new URIBuilder();
        uriBuilder.setScheme(MyProperties.getInstance().getProperty("nscheme"));
        uriBuilder.setHost(MyProperties.getInstance().getProperty("nhost"));
        uriBuilder.setPath(MyProperties.getInstance().getProperty("npath"));
        uriBuilder.setParameter("q", queryString);
        uriBuilder.setParameter("format","json");
        uriBuilder.setParameter("json_callback","cb");
        uriBuilder.setParameter("addressdetails","1");
        uriBuilder.setParameter("limit","10");

        URI uri = null;
        try {
            uri = uriBuilder.build();
        } catch (URISyntaxException e) {
            logger.catching(e);
        }
        return uri;
    }

    private String splitJsonString(String json){
        String returnString;
        int pos = json.indexOf( ")" );
        returnString = pos >= 0 ? json.substring( 0, pos ) : json;
        pos = returnString.indexOf("(");
        returnString =  pos >= 0 ? returnString.substring( pos + ")".length() ) : "";

        return returnString;
    }



    //****************************************
    // INNER CLASSES
    //****************************************

    //http://nominatim.openstreetmap.org/search?q=Berlin&format=json&addressdetails=1&limit=10

}

package de.hsmainz.pubapp.routing.controller;

import com.google.gson.Gson;
import de.hsmainz.pubapp.routing.model.APIKeys;
import de.hsmainz.pubapp.routing.model.geojson.GeoJsonCollection;
import de.hsmainz.pubapp.routing.model.graphhopperjson.GraphhopperJson;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author Sarah
 * @since 05.12.2016
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

    @Override
    public GeoJsonCollection requestRouting(String startPoint,
                                            String endPoint,
                                            String locale,
                                            String pointsEncoded) {
        // validation is now prior to this
        return doHttpGet(buildGraphhopperUri(startPoint, endPoint, locale, pointsEncoded));
    }

    //****************************************
    // PRIVATE METHODS
    //****************************************

    /**
     *
     * @param startPoint
     * @param endPoint
     * @param locale
     * @param pointsEncoded
     * @return
     */
    private URI buildGraphhopperUri (String startPoint,
                                     String endPoint,
                                     String locale,
                                     String pointsEncoded) {

        APIKeys apiKeys;

        try {
            apiKeys = APIKeys.readKeys();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            logger.catching(e);
            apiKeys = new APIKeys();
        }

        URIBuilder uriBuilder = new URIBuilder();
        uriBuilder.setScheme("https"); // always use HTTPS if available ;)
        uriBuilder.setHost("graphhopper.com");
        uriBuilder.setPath("/api/1/route");
        uriBuilder.addParameter("point", startPoint);
        uriBuilder.addParameter("point", endPoint);
        uriBuilder.setParameter("locale", locale);
        uriBuilder.setParameter("points_encoded", pointsEncoded);
        uriBuilder.addParameter("vehicle","foot");
        uriBuilder.setParameter("key", apiKeys.getGraphhopperKey());

        URI uri = null;
        try {
            uri = uriBuilder.build();
        } catch (URISyntaxException e) {
            e.printStackTrace();
            logger.catching(e);
        }

//        System.out.println(uri.toString()); // TODO remove; logging the link which will be called
        return uri;
    }

    private GeoJsonCollection doHttpGet(URI uri) {

        Gson gson = new Gson();
        GraphhopperJson graphhopperJson;
        GeoJsonCollection geoJsonCollection;
        HttpGet httpget = new HttpGet(uri);

        try(CloseableHttpClient httpclient = HttpClients.createDefault(); CloseableHttpResponse response = httpclient.execute(httpget)) {
            InputStream inputStream = response.getEntity().getContent();
            Reader reader = new InputStreamReader(inputStream, "UTF-8");
            graphhopperJson = gson.fromJson(reader, GraphhopperJson.class);
            geoJsonCollection = new GeoJsonCollection(graphhopperJson);
            inputStream.close();
        } catch (Exception e){
            geoJsonCollection = new GeoJsonCollection();
            e.printStackTrace();
            logger.catching(e);
        }
        return geoJsonCollection;
    }

    //****************************************
    // INNER CLASSES
    //****************************************

}

package de.hs_mainz.pubApp;

import com.google.gson.Gson;
import de.hs_mainz.pubApp.jsonparser.ClientInputJson;
import de.hs_mainz.pubApp.jsonparser.geoJson.GeoJsonColection;
import de.hs_mainz.pubApp.jsonparser.graphhopperJson.GrahhopperJson;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by Arno on 04.12.2016.
 */
public class HttpApiRequest {

    private String text;
    private String locale;


    public GeoJsonColection requestGraphhopperGeocoder(ClientInputJson inputJson)
    {
        Gson gson = new Gson();
        GrahhopperJson grahhopperJson;
        GeoJsonColection geoJsonColection;
        CloseableHttpClient httpclient = HttpClients.createDefault();

        URI uri = buildGraphhopperUri(inputJson);

        HttpGet httpget = new HttpGet(uri);

        try(CloseableHttpResponse response = httpclient.execute(httpget)) {
            InputStream tt = response.getEntity().getContent();
            Reader reader = new InputStreamReader(tt, "UTF-8");
            grahhopperJson = gson.fromJson(reader, GrahhopperJson.class);
            geoJsonColection = new GeoJsonColection(grahhopperJson);
        }
        catch (Exception e){
            geoJsonColection = new GeoJsonColection();
        }
        return geoJsonColection;
    }

    /***
     *
     * @param inputJson Class with input parameters
     * @return Uri for geocoder request on graphhopper API
     */
    private URI buildGraphhopperUri (ClientInputJson inputJson){

        APIKeys apiKeys;
        try {
            apiKeys = APIKeys.readKeys();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            apiKeys = new APIKeys();
        }

        URIBuilder uriBuilder = new URIBuilder();
        uriBuilder.setScheme("http");
        uriBuilder.setHost("graphhopper.com");
        uriBuilder.setPath("/api/1/geocode");
        uriBuilder.setParameter("q", inputJson.getQueryString());
        uriBuilder.setParameter("locale", inputJson.getLocale());
        uriBuilder.setParameter("key", apiKeys.getGraphhopperKey());
        URI uri = null;
        try {
            uri = uriBuilder.build();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return uri;
    }

    private boolean validateInput(ClientInputJson inputJson) {
        if (inputJson.getQueryString() == null || inputJson.getQueryString().isEmpty()) {
            return false;
        }
        if (inputJson.getLocale() == null || inputJson.getLocale().isEmpty()){
            return false;
        }
        if (inputJson.getLocale()!="de"||inputJson.getLocale()!="en"||inputJson.getLocale()!="fr"||inputJson.getLocale()!="it"){
            return false;
        }

        return true;
    }

    //private boolean validateOutput(GrahhopperJson grahhopperJson){

    //}

}

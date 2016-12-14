package de.hsmainz.pubApp.geocoder;

import com.google.gson.Gson;
import de.hsmainz.pubApp.geocoder.jsonparser.ClientInputJson;
import de.hsmainz.pubApp.geocoder.jsonparser.geoJson.GeoJsonColection;
import de.hsmainz.pubApp.geocoder.jsonparser.graphhopperJson.GrahhopperJson;
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

    public GeoJsonColection requestGraphhopperGeocoder(ClientInputJson inputJson)
    {

        URI uri = buildGraphhopperUri(inputJson);

        return doHttpGet(uri);

    }

    public GeoJsonColection requestGraphhopperGeocoder(String queryString, String locale)
    {

        URI uri = buildGraphhopperUri(queryString,locale);

        return doHttpGet(uri);

    }

    private GeoJsonColection doHttpGet(URI uri){

        Gson gson = new Gson();
        GrahhopperJson grahhopperJson;
        GeoJsonColection geoJsonColection;
        CloseableHttpClient httpclient = HttpClients.createDefault();

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

        return buildGraphhopperUri(inputJson.getQueryString(),inputJson.getLocale());

    }

    private URI buildGraphhopperUri (String queryString, String locale){

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
        uriBuilder.setParameter("q", queryString);
        uriBuilder.setParameter("locale", locale);
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

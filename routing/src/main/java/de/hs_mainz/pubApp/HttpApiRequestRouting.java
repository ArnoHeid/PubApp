package de.hs_mainz.pubApp;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import de.hs_mainz.pubApp.jsonparser.ClientInputJsonRouting;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils; // TODO remove

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by Sarah.
 */
public class HttpApiRequestRouting {

    public String requestGraphhopperRouting(ClientInputJsonRouting inputJson) {
        Gson gson = new Gson();
        String json = "";
        CloseableHttpClient httpclient = HttpClients.createDefault();

        URI uri = buildGraphhopperUri(inputJson);

        HttpGet httpget = new HttpGet(uri);

        try(CloseableHttpResponse response = httpclient.execute(httpget)) {
//            InputStream tt = response.getEntity().getContent();
//            Reader reader = new InputStreamReader(tt, "UTF-8");
//            json = response.getEntity().get;
            json = EntityUtils.toString(response.getEntity());
        }
        catch (Exception e){}

//        return inputJson.getLocale();
        return json;
    }

    /***
     *
     * @param inputJson Class with input parameters
     * @return Uri for geocoder request on graphhopper API
     */
    private URI buildGraphhopperUri (ClientInputJsonRouting inputJson){

        APIKeys apiKeys;
        try {
            apiKeys = APIKeys.readKeys();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            apiKeys = new APIKeys();
        }

        URIBuilder uriBuilder = new URIBuilder();
        uriBuilder.setScheme("https"); // always use HTTPS if available ;)
        uriBuilder.setHost("graphhopper.com");
        uriBuilder.setPath("/api/1/route");
        uriBuilder.addParameter("point", inputJson.getStartPoint());
        uriBuilder.addParameter("point", inputJson.getEndPoint());
        uriBuilder.setParameter("locale", inputJson.getLocale());
        uriBuilder.setParameter("points_encoded", inputJson.getPointsEncoded());
//        uriBuilder.setParameter("vehicle", inputJson.getVehicle()); TODO
        uriBuilder.setParameter("key", apiKeys.getGraphhopperKey());
        URI uri = null;
        try {
            uri = uriBuilder.build();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        System.out.println(uri.toString()); // TODO remove; logging the link which will be called
        return uri;
    }
}

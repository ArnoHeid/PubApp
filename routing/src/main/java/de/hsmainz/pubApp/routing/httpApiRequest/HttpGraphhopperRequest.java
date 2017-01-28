package de.hsmainz.pubApp.routing.httpApiRequest;

import com.google.gson.Gson;
import de.hsmainz.pubapp.geocoder.jsonparser.APIKeys;
import de.hsmainz.pubApp.routing.jsonparser.geoJson.GeoJsonCollection;
import de.hsmainz.pubApp.routing.jsonparser.graphhopperJson.GraphhopperJson;
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
 * Created by Sarah.
 */
public class HttpGraphhopperRequest implements HttpAPIRequest {

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

    /**
     *
     * @param startPoint
     * @param endPoint
     * @param locale
     * @param pointsEncoded
     * @return
     */
    private boolean validateInput(String startPoint,
                                  String endPoint,
                                  String locale,
                                  String pointsEncoded) {

        // locale could be null/empty/something else, since graphhopper defaults to "en"
        // https://graphhopper.com/api/1/docs/routing/
        if (locale == null || locale.isEmpty()){
            System.out.println("locale null or empty");
            return false;
        }
//        if (inputJson.getLocale() != "de" || inputJson.getLocale() != "en" || inputJson.getLocale() != "fr" || inputJson.getLocale() != "it"){
//            System.out.println("locale not supported");
//            return false;
//        }

        if (startPoint == null || startPoint.isEmpty() || endPoint == null || endPoint.isEmpty()) {
            System.out.println("start- and/or endpoint null or empty");
            return false;
        }
        // TODO? validate if start- & endpoint are proper points?
        // TODO? validate pointsEncoded?

        return true;
    }

    @Override
    public GeoJsonCollection requestRouting(String startPoint,
                                            String endPoint,
                                            String locale,
                                            String pointsEncoded) {

        if (validateInput(startPoint, endPoint, locale, pointsEncoded)) {
            return doHttpGet(buildGraphhopperUri(startPoint, endPoint, locale, pointsEncoded));
        }

        // …else return empty GeoJsonCollection
        return new GeoJsonCollection(); // TODO proper error handling?
    }

    private GeoJsonCollection doHttpGet(URI uri){

        Gson gson = new Gson();
        GraphhopperJson graphhopperJson;
        GeoJsonCollection geoJsonCollection;
        CloseableHttpClient httpclient = HttpClients.createDefault();

        HttpGet httpget = new HttpGet(uri);

        try(CloseableHttpResponse response = httpclient.execute(httpget)) {
            InputStream tt = response.getEntity().getContent();
            Reader reader = new InputStreamReader(tt, "UTF-8");
            graphhopperJson = gson.fromJson(reader, GraphhopperJson.class);
            geoJsonCollection = new GeoJsonCollection(graphhopperJson);
        } catch (Exception e){
            geoJsonCollection = new GeoJsonCollection();
        }
        return geoJsonCollection;
    }
}

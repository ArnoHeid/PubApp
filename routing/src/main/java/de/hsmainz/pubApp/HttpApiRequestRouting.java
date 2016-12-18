package de.hsmainz.pubApp;

import com.google.gson.Gson;
import de.hsmainz.pubApp.geocoder.APIKeys;
import de.hsmainz.pubApp.jsonparser.ClientInputJsonRouting;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils; // TODO remove

import java.io.FileNotFoundException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by Sarah.
 */
public class HttpApiRequestRouting {

    /**
     *
     * @param startPoint
     * @param endPoint
     * @param locale
     * @param pointsEncoded
     * @return
     */
    public String requestGraphhopperRouting(String startPoint,
                                            String endPoint,
                                            String locale,
                                            String pointsEncoded) {
        Gson gson = new Gson();
        String json = "";
        CloseableHttpClient httpclient = HttpClients.createDefault();

        if (validateInput(startPoint, endPoint, locale, pointsEncoded)) {
            URI uri = buildGraphhopperUri(startPoint, endPoint, locale, pointsEncoded);

            HttpGet httpget = new HttpGet(uri);

            try(CloseableHttpResponse response = httpclient.execute(httpget)) {
//            InputStream tt = response.getEntity().getContent();
//            Reader reader = new InputStreamReader(tt, "UTF-8");
//            json = response.getEntity().get;
                json = EntityUtils.toString(response.getEntity());
            }
            catch (Exception e){}

//        return inputJson.getLocale();
        } else {
            json = "validaton error"; // TODO proper error handling?
        }

        return json;
    }

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
                                     String pointsEncoded){

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

        return true;
    }
}

package de.hs_mainz.pubApp;

import com.google.gson.Gson;
import de.hs_mainz.pubApp.jsonparser.MainJson;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * Created by Arno on 04.12.2016.
 */
public class HttpApiRequest {

    private String text;
    private String locale;


    public MainJson requestGraphhopperGeocoder(){
        Gson gson = new Gson();
        MainJson MainJson;
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpget = new HttpGet(
                "https://graphhopper.com/api/1/geocode?q=berlin&locale=de&debug=true&key=e11a85db-7ff3-48ba-b365-da3538ae534a");

        try(CloseableHttpResponse response = httpclient.execute(httpget)) {
            InputStream tt = response.getEntity().getContent();
            Reader reader = new InputStreamReader(tt, "UTF-8");
            MainJson = gson.fromJson(reader, MainJson.class);
        }
        catch (Exception e){
            MainJson = new MainJson();
        }
        return MainJson;
    }


}

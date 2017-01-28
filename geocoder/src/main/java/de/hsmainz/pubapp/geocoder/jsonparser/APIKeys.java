package de.hsmainz.pubapp.geocoder.jsonparser;

import com.google.gson.Gson;

import java.io.*;

/**
 * Created by Arno on 09.12.2016.
 */
public class APIKeys {
    private String googleKey;
    private String graphhopperKey;

    public String getGoogleKey() {
        return googleKey;
    }

    public String getGraphhopperKey() {
        return graphhopperKey;
    }

    public static APIKeys readKeys() throws FileNotFoundException {
        Gson gson = new Gson();
        File jsonFile = new File("APIKeys.key");
        FileReader fileReader = new FileReader(jsonFile);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        return gson.fromJson(bufferedReader, APIKeys.class);
    }

}

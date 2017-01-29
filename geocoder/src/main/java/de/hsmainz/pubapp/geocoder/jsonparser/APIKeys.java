package de.hsmainz.pubapp.geocoder.jsonparser;

import com.google.gson.Gson;

import java.io.*;

/**
 * Reads Keys for use with APIs
 *
 * @author Arno
 * @since 09.12.2016.
 */
public class APIKeys {

    //****************************************
    // CONSTANTS
    //****************************************

    //****************************************
    // VARIABLES
    //****************************************

    private String googleKey;
    private String graphhopperKey;

    //****************************************
    // INIT/CONSTRUCTOR
    //****************************************

    //****************************************
    // GETTER/SETTER
    //****************************************

    public String getGoogleKey() {
        return googleKey;
    }

    public String getGraphhopperKey() {
        return graphhopperKey;
    }

    //****************************************
    // PUBLIC METHODS
    //****************************************

    public static APIKeys readKeys() throws FileNotFoundException {
        Gson gson = new Gson();
        File jsonFile = new File("APIKeys.key");
        FileReader fileReader = new FileReader(jsonFile);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        return gson.fromJson(bufferedReader, APIKeys.class);
    }

    //****************************************
    // PRIVATE METHODS
    //****************************************

    //****************************************
    // INNER CLASSES
    //****************************************

}

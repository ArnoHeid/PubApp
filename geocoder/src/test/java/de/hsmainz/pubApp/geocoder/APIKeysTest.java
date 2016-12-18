package de.hsmainz.pubApp.geocoder;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Arno on 13.12.2016.
 */
public class APIKeysTest {
    @Test
    public void getGoogleKey() throws Exception {
        APIKeys apiKeys = APIKeys.readKeys();
        String googleKey = apiKeys.getGoogleKey();
        assertTrue(!googleKey.isEmpty()||googleKey !="");
    }

    @Test
    public void getGraphhopperKey() throws Exception {
        APIKeys apiKeys = APIKeys.readKeys();
        String graphhopperKey = apiKeys.getGraphhopperKey();
        assertTrue(!graphhopperKey.isEmpty()||graphhopperKey !="");

    }

    @Test
    public void readKeys() throws Exception {

    }

}
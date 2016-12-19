package de.hsmainz.pubApp.geocoder;

import de.hsmainz.pubApp.geocoder.jsonparser.APIKeys;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Arno on 13.12.2016.
 */
public class APIKeysTest {

    @Test
    public void readKeys() throws Exception {
        APIKeys apiKeys = APIKeys.readKeys();
        String graphhopperKey = apiKeys.getGraphhopperKey();
        assertTrue(!graphhopperKey.isEmpty()||graphhopperKey !="");
        String googleKey = apiKeys.getGoogleKey();
        assertTrue(!googleKey.isEmpty()||googleKey !="");
    }

}
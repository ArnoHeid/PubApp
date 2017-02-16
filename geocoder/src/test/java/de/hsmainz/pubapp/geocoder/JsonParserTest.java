package de.hsmainz.pubapp.geocoder;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import de.hsmainz.pubapp.geocoder.model.geojson.Features;
import de.hsmainz.pubapp.geocoder.model.geojson.GeoJsonCollection;
import de.hsmainz.pubapp.geocoder.model.geojson.Geometry;
import de.hsmainz.pubapp.geocoder.model.geojson.Properties;
import de.hsmainz.pubapp.geocoder.model.graphhopperjson.GrahhopperJson;
import de.hsmainz.pubapp.geocoder.model.nominatimjson.NominatimJson;
import org.junit.Test;

import java.lang.reflect.Type;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Tests all JsonParsers
 *
 * @author Arno
 * @since 07.02.2017.
 */
public class JsonParserTest {

    //TODO Testbeschreibungen

    /**
     *
     *
     *
     *
     */
    @Test
    public void graohhopperJsonTest() {

        //Testdata

        String graphhopperJson = "{ \"hits\": [ { \"osm_id\": 62422, \"osm_type\": \"R\", " +
                "\"extent\": [ 13.08835, 52.67551, 13.76116, 52.33826 ], \"country\": \"Deutschland\", " +
                "\"osm_key\": \"place\", \"osm_value\": \"state\", \"name\": \"Berlin\", \"point\": {" +
                " \"lng\": 13.4385964, \"lat\": 52.5198535 } }, { \"osm_id\": 240109189, \"osm_type\": " +
                "\"N\", \"country\": \"Deutschland\", \"osm_key\": \"place\", \"city\": \"Berlin\", " +
                "\"osm_value\": \"city\", \"name\": \"Berlin\", \"state\": \"Berlin\", \"point\": { " +
                "\"lng\": 13.3888599, \"lat\": 52.5170365 } } ], \"locale\": \"de\"}";

        double delta = 0.0000001;

        //Test

        Gson gson = new Gson();
        GeoJsonCollection geoJsonToTest = new GeoJsonCollection(gson.fromJson(graphhopperJson, GrahhopperJson.class));
        Features featuresToTest = geoJsonToTest.getFeatures().get(0);
        Geometry geometryToTest = featuresToTest.getGeometry();
        Properties propertiesToTest = featuresToTest.getProperties();

        //Asserts

        assertNotNull("GeoJsonCollection must not be Null", geoJsonToTest);
        assertEquals("GeoJsonCollection type should be equal to \"FeatureCollection\" ", "FeatureCollection", geoJsonToTest.getType());

        //Test Features

        assertNotNull("GeoJson feature must not be Null", geoJsonToTest.getFeatures());
        assertEquals("GeoJson feature list length should be 2", 2, geoJsonToTest.getFeatures().size());
        assertEquals("GeoJson type should be equal to \"Feature\" ", "Feature", featuresToTest.getType());

        //Test Geometry

        assertNotNull("Feature Geometry must not be Null", geometryToTest);
        assertEquals("Feature type should be equal to \"Point\" ", "Point", geometryToTest.getType());
        double[] testcoords = {13.4385964, 52.5198535};
        assertArrayEquals("Coordinats should match testcoordinats ", testcoords, geometryToTest.getCoordinates(), delta);

        //Test Properties

        assertNotNull("Feature Properties must not be Null", propertiesToTest);
        assertEquals("Country should match \"Deutschland\"", "Deutschland", propertiesToTest.getCountry());
        assertEquals("Country should match \"Berlin\"", "Berlin", propertiesToTest.getName());

    }

    /**
     *
     */
    @Test
    public void nominatimJsonTest() {

        //Testdata

        String nominatimJson = "[{\"place_id\":\"202558748\",\"licence\":\"Data © OpenStreetMap contributors, ODbL 1.0. http:" +
                "www.openstreetmap.orgcopyright\",\"osm_type\":\"node\",\"osm_id\":\"240028377\",\"boundingb" +
                "ox\":[\"49.9233\",\"50.2433\",\"8.09\",\"8.41\"],\"lat\":\"50.0833\",\"lon\":\"8.25\",\"display_name\":\"" +
                "Wiesbaden, Regierungsbezirk Darmstadt, Hessen, Deutschland\",\"class\":\"place\",\"type\":\"city\",\"imp" +
                "ortance\":0.73904844441682,\"icon\":\"http:nominatim.openstreetmap.orgimagemapiconspoi_place_" +
                "city.p.20.png\",\"address\":{\"city\":\"Wiesbaden\",\"state_district\":\"Regierungsbezirk Darmstadt\",\"" +
                "state\":\"Hessen\",\"country\":\"Deutschland\",\"country_code\":\"de\"}},{\"place_id\":\"157903662\",\"l" +
                "icence\":\"Data © OpenStreetMap contributors, ODbL 1.0. http:www.openstreetmap.orgcopyright\",\"osm" +
                "_type\":\"relation\",\"osm_id\":\"62496\",\"boundingbox\":[\"49.9932752\",\"50.1520225\",\"8.1106032\",\"8" +
                ".3861874\"],\"lat\":\"50.07259955\",\"lon\":\"8.25405388400511\",\"display_name\":\"Wiesbaden, Regierungsbe" +
                "zirk Darmstadt, Hessen, Deutschland\",\"class\":\"place\",\"type\":\"city\",\"importance\":0.73904844441682" +
                ",\"icon\":\"http:nominatim.openstreetmap.orgimagesmapiconspoi_place_city.p.20.png\",\"address\":{\"" +
                "city\":\"Wiesbaden\",\"county\":\"Wiesbaden\",\"state_district\":\"Regierungsbezirk Darmstadt\",\"state\":\"" +
                "Hessen\",\"country\":\"Deutschland\",\"country_code\":\"de\"}}]";

        double delta = 0.0000001;

        //Test

        Gson gson = new Gson();

        Type listType = new TypeToken<List<NominatimJson>>() {
        }.getType();
        List<NominatimJson> nominatimJsonList = gson.fromJson(nominatimJson, listType);

        GeoJsonCollection geoJsonToTest = new GeoJsonCollection(nominatimJsonList);
        Features featuresToTest = geoJsonToTest.getFeatures().get(0);
        Geometry geometryToTest = featuresToTest.getGeometry();
        Properties propertiesToTest = featuresToTest.getProperties();

        //Asserts

        assertNotNull("GeoJsonCollection must not be Null", geoJsonToTest);
        assertEquals("GeoJsonCollection type should be equal to \"FeatureCollection\" ", "FeatureCollection", geoJsonToTest.getType());

        //Test Features

        assertNotNull("GeoJson feature must not be Null", geoJsonToTest.getFeatures());
        assertEquals("GeoJson feature list length should be 2", 2, geoJsonToTest.getFeatures().size());
        assertEquals("GeoJson type should be equal to \"Feature\" ", "Feature", featuresToTest.getType());

        //Test Geometry

        assertNotNull("Feature Geometry must not be Null", geometryToTest);
        assertEquals("Feature type should be equal to \"Point\" ", "Point", geometryToTest.getType());
        double[] testcoords = {8.25, 50.0833};
        assertArrayEquals("Coordinats should match testcoordinats ", testcoords, geometryToTest.getCoordinates(), delta);

        //Test Properties

        assertNotNull("Feature Properties must not be Null", propertiesToTest);
        assertEquals("Country should match \"Deutschland\"", "Deutschland", propertiesToTest.getCountry());
        String testname = "Wiesbaden, Regierungsbezirk Darmstadt, Hessen, Deutschland";
        assertEquals("Country should match \"Berlin\"", testname, propertiesToTest.getName());

    }

}

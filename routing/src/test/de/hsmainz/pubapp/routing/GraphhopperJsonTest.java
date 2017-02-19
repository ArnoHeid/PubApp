package de.hsmainz.pubapp.routing;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import de.hsmainz.pubapp.routing.model.geojson.GeoJson;
import de.hsmainz.pubapp.routing.model.geojson.GeoJsonCollection;
import de.hsmainz.pubapp.routing.model.geojson.Properties;
import de.hsmainz.pubapp.routing.model.graphhopperjson.GraphhopperJson;
import org.junit.Test;

import java.nio.file.Paths;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Sarah
 * @since 19.02.2017.
 */
public class GraphhopperJsonTest {

    /**
     *
     */
    @Test
    public void graphhopperJsonTest() {

        // Data
        String graphhopperJson = "{\"hints\":{\"visited_nodes.average\":\"126.0\",\"visited_nodes.sum\":\"126\"},\"paths\":[{\"instructions\":[{\"distance\":175.88,\"sign\":0,\"interval\":[0,3],\"text\":\"Geradeaus auf Lindenstraße\",\"time\":126633},{\"distance\":126.73,\"sign\":2,\"interval\":[3,6],\"text\":\"Rechts abbiegen auf Hermannstraße\",\"time\":91244},{\"distance\":177.08,\"sign\":3,\"interval\":[6,14],\"text\":\"Scharf rechts abbiegen auf Mittelstraße\",\"time\":127496},{\"distance\":23.325,\"sign\":-2,\"interval\":[14,16],\"text\":\"Links abbiegen\",\"time\":16794},{\"distance\":43.218,\"sign\":-2,\"interval\":[16,17],\"text\":\"Links abbiegen auf Kölner Straße, B 62\",\"time\":31116},{\"distance\":652.602,\"sign\":2,\"interval\":[17,34],\"text\":\"Rechts abbiegen auf Hauptstraße\",\"time\":469869},{\"distance\":757.071,\"sign\":-1,\"interval\":[34,49],\"text\":\"Leicht links abbiegen auf Löhrstraße\",\"time\":545090},{\"distance\":3559.158,\"sign\":0,\"interval\":[49,117],\"text\":\"Geradeaus\",\"time\":2562582},{\"distance\":0,\"sign\":4,\"interval\":[117,117],\"text\":\"Ziel erreicht!\",\"time\":0}],\"descend\":153.6049346923828,\"ascend\":306.6049346923828,\"distance\":5515.064,\"bbox\":[7.957357,50.810441,7.987499,50.838928],\"weight\":3405.539933,\"time\":3970824,\"points_encoded\":false,\"points\":{\"coordinates\":[[7.957357,50.838306],[7.958229,50.838489],[7.958534,50.838679],[7.959565,50.838928],[7.960031,50.838238],[7.960137,50.838016],[7.960236,50.837889],[7.959278,50.837768],[7.959022,50.83771],[7.958913,50.83763],[7.958882,50.837548],[7.958923,50.83743],[7.958942,50.837264],[7.958901,50.837146],[7.958788,50.837035],[7.958845,50.83694],[7.958995,50.836887],[7.959513,50.837085],[7.960544,50.836197],[7.96068,50.836017],[7.960737,50.835855],[7.960722,50.83574],[7.960602,50.835553],[7.959782,50.835151],[7.959596,50.834927],[7.959446,50.834278],[7.959472,50.834151],[7.959208,50.833782],[7.9589,50.833655],[7.958253,50.833272],[7.957778,50.832883],[7.95767,50.832714],[7.957588,50.832526],[7.957598,50.832374],[7.957631,50.832259],[7.957851,50.832132],[7.958096,50.832041],[7.959375,50.831717],[7.960266,50.831584],[7.960726,50.831455],[7.961053,50.831433],[7.961552,50.831317],[7.961948,50.831188],[7.962504,50.830981],[7.962937,50.830762],[7.963299,50.830305],[7.963492,50.830092],[7.963946,50.830099],[7.966537,50.830292],[7.967121,50.830376],[7.9673,50.830404],[7.967607,50.83049],[7.968357,50.830733],[7.968481,50.830681],[7.968587,50.830501],[7.968569,50.83041],[7.968351,50.830006],[7.968335,50.829864],[7.96841,50.82966],[7.968941,50.828831],[7.96937,50.828418],[7.970099,50.827937],[7.970722,50.827564],[7.971548,50.827144],[7.97246,50.826812],[7.973297,50.826642],[7.973994,50.826527],[7.975035,50.826263],[7.975579,50.825817],[7.976075,50.825653],[7.97632,50.825344],[7.976288,50.825091],[7.97604,50.824844],[7.975956,50.824716],[7.975839,50.824648],[7.975615,50.824424],[7.975477,50.824248],[7.975313,50.823984],[7.97515,50.823669],[7.975007,50.823173],[7.975161,50.822342],[7.975192,50.822],[7.975124,50.821627],[7.974982,50.821342],[7.974798,50.821105],[7.974377,50.820869],[7.974847,50.820266],[7.975382,50.819787],[7.975265,50.819655],[7.973729,50.818152],[7.973976,50.818063],[7.973898,50.817716],[7.973734,50.81719],[7.973382,50.816409],[7.974192,50.816243],[7.973884,50.814412],[7.974198,50.814252],[7.977451,50.81432],[7.978824,50.814472],[7.979807,50.814475],[7.97984,50.814359],[7.980149,50.814068],[7.981034,50.813846],[7.981952,50.813797],[7.982404,50.813697],[7.982586,50.813631],[7.982939,50.813437],[7.983069,50.813321],[7.983825,50.812935],[7.984278,50.812731],[7.985399,50.812095],[7.98565,50.812017],[7.985951,50.811876],[7.986271,50.811671],[7.987034,50.811049],[7.987318,50.810777],[7.987499,50.810441],[7.987499,50.810441]],\"type\":\"LineString\"},\"snapped_waypoints\":{\"coordinates\":[[7.957357,50.838306],[7.987499,50.810441]],\"type\":\"LineString\"}}],\"info\":{\"took\":6,\"copyrights\":[\"GraphHopper\",\"OpenStreetMap contributors\"]}}";

        // Test
        Gson gson = new Gson();
        GeoJsonCollection geoJsonCollection = new GeoJsonCollection(gson.fromJson(graphhopperJson, GraphhopperJson.class));
        GeoJson geoJson = geoJsonCollection.getFeatures().get(0);
        JsonObject geometryToTest = geoJson.getGeometry();
        Properties propertiesToTest = geoJson.getProperties();

        // Asserts
        assertNotNull("GeoJsonCollection must not be Null", geoJsonCollection);
        assertEquals("GeoJsonCollection type should be equal to \"FeatureCollection\" ", "FeatureCollection", geoJsonCollection.getType());

        // Test GeoJson
        assertNotNull("GeoJson feature must not be Null", geoJson);
        assertEquals("GeoJson feature list length should be 1", 1, geoJsonCollection.getFeatures().size());
        assertEquals("GeoJson type should be equal to \"Feature\" ", "Feature", geoJson.getType());

        // Test Geometry
        assertNotNull("Feature Geometry must not be Null", geometryToTest);
        assertEquals("Feature type should be equal to \"LineString\" ", "LineString", geometryToTest.get("type").getAsString());
        double[] startPoint = {50.838056,7.956944};
        double[] endPoint = {50.810441,7.987499};
        double delta = 0.0005;
        JsonArray coordinates = geometryToTest.getAsJsonArray("coordinates");
        JsonArray firstCoordinate = coordinates.get(0).getAsJsonArray();
        JsonArray lastCoordinate = coordinates.get(coordinates.size()-1).getAsJsonArray();
        double[] actualStartPoint = {firstCoordinate.get(1).getAsDouble(), firstCoordinate.get(0).getAsDouble()};
        double[] actualEndPoint = {lastCoordinate.get(1).getAsDouble(), lastCoordinate.get(0).getAsDouble()};
        assertArrayEquals("Coordinats (start) should match testcoordinats ", startPoint, actualStartPoint, delta);
        assertArrayEquals("Coordinats (end) should match testcoordinats ", endPoint, actualEndPoint, delta);

        // Test Properties
        assertNotNull("Feature Properties must not be Null", propertiesToTest);

    }
}

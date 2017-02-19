package de.hsmainz.pubapp.poi.web;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import de.hsmainz.pubapp.poi.controller.PoiSearchInputController;
import de.hsmainz.pubapp.poi.model.Coordinate;
import de.hsmainz.pubapp.poi.model.SelectedSearchCriteria;

public class ResponseHandlerTest {

	@Test
	public void testOverpassBbox() {
		String geoJsonResult = createSearch("overpass", "bbox");
		String expectedResult = "{\"type\":\"FeatureCollection\",\"features\":[{\"type\":\"Feature\",\"properties\":{\"name\":\"Caveau\",\"interest\":\"bar\",\"openingHours\":\"Es liegen keine Öffnungszeiten vor\",\"isOpen\":\"Keine Informationen ob gerade geöffnet\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[8.2660985,49.9989015]}},{\"type\":\"Feature\",\"properties\":{\"name\":\"Filmriss\",\"interest\":\"bar\",\"openingHours\":\"Es liegen keine Öffnungszeiten vor\",\"isOpen\":\"Keine Informationen ob gerade geöffnet\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[8.2667046,49.9997434]}},{\"type\":\"Feature\",\"properties\":{\"name\":\"Chapeau Mainz\",\"interest\":\"bar\",\"openingHours\":\"Es liegen keine Öffnungszeiten vor\",\"isOpen\":\"Keine Informationen ob gerade geöffnet\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[8.2658764,49.9999117]}},{\"type\":\"Feature\",\"properties\":{\"interest\":\"atm\",\"openingHours\":\"Es liegen keine Öffnungszeiten vor\",\"isOpen\":\"Keine Informationen ob gerade geöffnet\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[8.2625603,50.0006483]}},{\"type\":\"Feature\",\"properties\":{\"name\":\"Postbank\",\"interest\":\"atm\",\"openingHours\":\"Es liegen keine Öffnungszeiten vor\",\"isOpen\":\"Keine Informationen ob gerade geöffnet\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[8.262955,50.0006924]}},{\"type\":\"Feature\",\"properties\":{\"name\":\"Geldautomat Cashgroup\",\"interest\":\"atm\",\"openingHours\":\"Es liegen keine Öffnungszeiten vor\",\"isOpen\":\"Keine Informationen ob gerade geöffnet\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[8.2647123,50.0010038]}}]}";
		assertNotNull("Result should not be null", geoJsonResult);
		assertEquals(geoJsonResult, expectedResult);
	}

	@Test
	public void testGoogleRadius() {
		// INFO: Make sure radius in properties is 1000
		String geoJsonResult = createSearch("google", "radius");
		String expectedResult = "{\"type\":\"FeatureCollection\",\"features\":[{\"type\":\"Feature\",\"properties\":{\"name\":\"SAUSALITOS Mainz\",\"interest\":\"bar\",\"openingHours\":\"Es liegen keine Öffnungszeiten vor\",\"isOpen\":\"true\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[8.2717408,50.0025196]}},{\"type\":\"Feature\",\"properties\":{\"name\":\"Laurenz\",\"interest\":\"bar\",\"openingHours\":\"Es liegen keine Öffnungszeiten vor\",\"isOpen\":\"true\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[8.261924,50.0045095]}},{\"type\":\"Feature\",\"properties\":{\"name\":\"ReiseBank AG Mainz Hauptbahnhof\",\"interest\":\"atm\",\"openingHours\":\"Es liegen keine Öffnungszeiten vor\",\"isOpen\":\"false\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[8.259048,50.001535]}},{\"type\":\"Feature\",\"properties\":{\"name\":\"Onkel Willy´s Pub\",\"interest\":\"bar\",\"openingHours\":\"Es liegen keine Öffnungszeiten vor\",\"isOpen\":\"true\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[8.2628682,50.0000334]}},{\"type\":\"Feature\",\"properties\":{\"name\":\"Geldautomat Pax-Bank eG\",\"interest\":\"atm\",\"openingHours\":\"Es liegen keine Öffnungszeiten vor\",\"isOpen\":\"true\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[8.27145,49.99742999999999]}},{\"type\":\"Feature\",\"properties\":{\"name\":\"Zur Hölle\",\"interest\":\"bar\",\"openingHours\":\"Es liegen keine Öffnungszeiten vor\",\"isOpen\":\"true\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[8.2698137,49.9989399]}},{\"type\":\"Feature\",\"properties\":{\"name\":\"Mainzer Volksbank eG\",\"interest\":\"atm\",\"openingHours\":\"Es liegen keine Öffnungszeiten vor\",\"isOpen\":\"true\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[8.247654799999998,50.00048359999999]}},{\"type\":\"Feature\",\"properties\":{\"name\":\"Mainzer Volksbank eG\",\"interest\":\"atm\",\"openingHours\":\"Es liegen keine Öffnungszeiten vor\",\"isOpen\":\"true\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[8.266779999999999,50.00656000000001]}},{\"type\":\"Feature\",\"properties\":{\"name\":\"Mainzer Volksbank eG\",\"interest\":\"atm\",\"openingHours\":\"Es liegen keine Öffnungszeiten vor\",\"isOpen\":\"false\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[8.265537000000002,50.002263]}},{\"type\":\"Feature\",\"properties\":{\"name\":\"Filmriss Bar Mainz\",\"interest\":\"bar\",\"openingHours\":\"Es liegen keine Öffnungszeiten vor\",\"isOpen\":\"false\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[8.266798,49.999754]}},{\"type\":\"Feature\",\"properties\":{\"name\":\"Nirgendwo\",\"interest\":\"bar\",\"openingHours\":\"Es liegen keine Öffnungszeiten vor\",\"isOpen\":\"true\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[8.259772799999999,50.00436629999999]}},{\"type\":\"Feature\",\"properties\":{\"name\":\"Stadtbalkon\",\"interest\":\"bar\",\"openingHours\":\"Es liegen keine Öffnungszeiten vor\",\"isOpen\":\"true\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[8.2711092,49.9986097]}},{\"type\":\"Feature\",\"properties\":{\"name\":\"Irish Pub\",\"interest\":\"bar\",\"openingHours\":\"Es liegen keine Öffnungszeiten vor\",\"isOpen\":\"true\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[8.272719999999998,49.99543999999999]}},{\"type\":\"Feature\",\"properties\":{\"name\":\"Deutsche Bank\",\"interest\":\"atm\",\"openingHours\":\"Es liegen keine Öffnungszeiten vor\",\"isOpen\":\"Keine Informationen ob gerade geöffnet\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[8.2556176,50.00634549999999]}},{\"type\":\"Feature\",\"properties\":{\"name\":\"Commerzbank Mainz\",\"interest\":\"atm\",\"openingHours\":\"Es liegen keine Öffnungszeiten vor\",\"isOpen\":\"false\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[8.266764,50.002783]}},{\"type\":\"Feature\",\"properties\":{\"name\":\"Geldautomat - Euronet\",\"interest\":\"atm\",\"openingHours\":\"Es liegen keine Öffnungszeiten vor\",\"isOpen\":\"true\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[8.270960700000002,50.0016904]}},{\"type\":\"Feature\",\"properties\":{\"name\":\"Deutsche Apotheker- und Ärztebank eG - apoBank\",\"interest\":\"atm\",\"openingHours\":\"Es liegen keine Öffnungszeiten vor\",\"isOpen\":\"false\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[8.2609538,49.9928689]}},{\"type\":\"Feature\",\"properties\":{\"name\":\"Sparkasse Mainz - Geldautomat Römerpassage\",\"interest\":\"atm\",\"openingHours\":\"Es liegen keine Öffnungszeiten vor\",\"isOpen\":\"true\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[8.268021,50.0014003]}},{\"type\":\"Feature\",\"properties\":{\"name\":\"Mainzer Volksbank eG\",\"interest\":\"atm\",\"openingHours\":\"Es liegen keine Öffnungszeiten vor\",\"isOpen\":\"true\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[8.267139,49.994855]}},{\"type\":\"Feature\",\"properties\":{\"name\":\"Dorett Bar\",\"interest\":\"bar\",\"openingHours\":\"Es liegen keine Öffnungszeiten vor\",\"isOpen\":\"false\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[8.2628714,50.0023973]}},{\"type\":\"Feature\",\"properties\":{\"name\":\"Babylon Shisha Lounge\",\"interest\":\"bar\",\"openingHours\":\"Es liegen keine Öffnungszeiten vor\",\"isOpen\":\"Keine Informationen ob gerade geöffnet\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[8.2636233,50.0013292]}},{\"type\":\"Feature\",\"properties\":{\"name\":\"Santander Consumer Bank AG Filiale Mainz\",\"interest\":\"atm\",\"openingHours\":\"Es liegen keine Öffnungszeiten vor\",\"isOpen\":\"false\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[8.2652245,50.00148249999999]}},{\"type\":\"Feature\",\"properties\":{\"name\":\"Sixties\",\"interest\":\"bar\",\"openingHours\":\"Es liegen keine Öffnungszeiten vor\",\"isOpen\":\"true\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[8.26642,49.99994]}},{\"type\":\"Feature\",\"properties\":{\"name\":\"Zum Heringsbrunnen\",\"interest\":\"bar\",\"openingHours\":\"Es liegen keine Öffnungszeiten vor\",\"isOpen\":\"false\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[8.2721625,49.9960236]}},{\"type\":\"Feature\",\"properties\":{\"name\":\"head office Sparkasse Mainz\",\"interest\":\"atm\",\"openingHours\":\"Es liegen keine Öffnungszeiten vor\",\"isOpen\":\"false\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[8.2630313,50.00028889999999]}},{\"type\":\"Feature\",\"properties\":{\"name\":\"Hubert Bar\",\"interest\":\"bar\",\"openingHours\":\"Es liegen keine Öffnungszeiten vor\",\"isOpen\":\"false\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[8.267474,49.995115]}},{\"type\":\"Feature\",\"properties\":{\"name\":\"Sparda-Bank Südwest eG\",\"interest\":\"atm\",\"openingHours\":\"Es liegen keine Öffnungszeiten vor\",\"isOpen\":\"false\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[8.2609097,50.0028319]}},{\"type\":\"Feature\",\"properties\":{\"name\":\"Geldautomat Pax-Bank eG\",\"interest\":\"atm\",\"openingHours\":\"Es liegen keine Öffnungszeiten vor\",\"isOpen\":\"true\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[8.270279999999998,49.98938]}},{\"type\":\"Feature\",\"properties\":{\"name\":\"The Porter House Irish Pub\",\"interest\":\"bar\",\"openingHours\":\"Es liegen keine Öffnungszeiten vor\",\"isOpen\":\"true\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[8.269332799999999,49.99895850000001]}},{\"type\":\"Feature\",\"properties\":{\"name\":\"ING-DiBa - Geldautomat\",\"interest\":\"atm\",\"openingHours\":\"Es liegen keine Öffnungszeiten vor\",\"isOpen\":\"false\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[8.274922399999998,50.0007825]}},{\"type\":\"Feature\",\"properties\":{\"name\":\"TARGOBANK\",\"interest\":\"atm\",\"openingHours\":\"Es liegen keine Öffnungszeiten vor\",\"isOpen\":\"false\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[8.269529,49.99798500000001]}},{\"type\":\"Feature\",\"properties\":{\"name\":\"Sparkasse Mainz - Geldautomat Stadthaus\",\"interest\":\"atm\",\"openingHours\":\"Es liegen keine Öffnungszeiten vor\",\"isOpen\":\"true\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[8.260964999999999,50.002512]}},{\"type\":\"Feature\",\"properties\":{\"name\":\"Sparkasse Mainz - Geldautomat\",\"interest\":\"atm\",\"openingHours\":\"Es liegen keine Öffnungszeiten vor\",\"isOpen\":\"true\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[8.259844700000002,49.99272140000001]}},{\"type\":\"Feature\",\"properties\":{\"name\":\"Santander Bank\",\"interest\":\"atm\",\"openingHours\":\"Es liegen keine Öffnungszeiten vor\",\"isOpen\":\"false\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[8.270991299999999,50.00001959999999]}},{\"type\":\"Feature\",\"properties\":{\"name\":\"Chapeau-Mainz\",\"interest\":\"bar\",\"openingHours\":\"Es liegen keine Öffnungszeiten vor\",\"isOpen\":\"Keine Informationen ob gerade geöffnet\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[8.2661368,49.9999086]}},{\"type\":\"Feature\",\"properties\":{\"name\":\"Besitos Mainz\",\"interest\":\"bar\",\"openingHours\":\"Es liegen keine Öffnungszeiten vor\",\"isOpen\":\"true\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[8.260372299999998,50.0015691]}},{\"type\":\"Feature\",\"properties\":{\"name\":\"Der Große Gatsby\",\"interest\":\"bar\",\"openingHours\":\"Es liegen keine Öffnungszeiten vor\",\"isOpen\":\"true\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[8.264199999999999,49.99536]}},{\"type\":\"Feature\",\"properties\":{\"name\":\"Shooter Stars\",\"interest\":\"bar\",\"openingHours\":\"Es liegen keine Öffnungszeiten vor\",\"isOpen\":\"false\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[8.2590819,50.0023819]}},{\"type\":\"Feature\",\"properties\":{\"name\":\"Good Time\",\"interest\":\"bar\",\"openingHours\":\"Es liegen keine Öffnungszeiten vor\",\"isOpen\":\"true\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[8.2644766,50.00245409999999]}},{\"type\":\"Feature\",\"properties\":{\"name\":\"Bar jeder Sicht\",\"interest\":\"bar\",\"openingHours\":\"Es liegen keine Öffnungszeiten vor\",\"isOpen\":\"true\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[8.264000000000001,50.00239999999999]}}]}";
		assertNotNull("Result should not be null", geoJsonResult);
		assertEquals(geoJsonResult, expectedResult);
	}

	private String createSearch(String api, String searchType) {
		ResponseHandler responseHandler = new ResponseHandler();
		PoiSearchInputController poiInputController = new PoiSearchInputController();
		SelectedSearchCriteria criteria = new SelectedSearchCriteria();
		criteria.setInterests(createInterest());
		criteria.setCoordinates(createCoordinates());

		return responseHandler.getResponse(poiInputController.getPoisForCriteria(criteria, searchType, api));
	}

	private List<String> createInterest() {
		List<String> interests = new ArrayList<>();
		interests.add("bar");
		interests.add("atm");
		return interests;
	}

	private List<Coordinate> createCoordinates() {
		Coordinate coordOne = new Coordinate();
		coordOne.setLat(49.99837);
		coordOne.setLng(8.258301);
		Coordinate coordTwo = new Coordinate();
		coordTwo.setLat(50.001499);
		coordTwo.setLng(8.267261);
		Coordinate coordThree = new Coordinate();
		coordThree.setLat(49.981758);
		coordThree.setLng(8.244128);
		Coordinate coordFour = new Coordinate();
		coordFour.setLat(50.065531);
		coordFour.setLng(8.243553);
		Coordinate coordFive = new Coordinate();
		coordFive.setLat(49.99837);
		coordFive.setLng(8.258301);
		Coordinate coordSix = new Coordinate();
		coordSix.setLat(50.001499);
		coordSix.setLng(8.267261);
		Coordinate coordSeven = new Coordinate();
		coordSeven.setLat(49.981758);
		coordSeven.setLng(8.244128);
		Coordinate coordEight = new Coordinate();
		coordEight.setLat(50.0631);
		coordEight.setLng(8.2433);
		Coordinate coordNine = new Coordinate();
		coordNine.setLat(49.99837);
		coordNine.setLng(8.258301);
		List<Coordinate> coords = new ArrayList<>();
		coords.add(coordOne);
		coords.add(coordTwo);
		coords.add(coordThree);
		coords.add(coordFour);
		coords.add(coordFive);
		coords.add(coordSix);
		coords.add(coordSeven);
		coords.add(coordEight);
		coords.add(coordNine);

		return coords;
	}

}

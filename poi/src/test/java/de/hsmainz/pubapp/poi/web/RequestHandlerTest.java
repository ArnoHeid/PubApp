package de.hsmainz.pubapp.poi.web;

import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.google.gson.Gson;

import de.hsmainz.pubapp.poi.model.Coordinate;
import de.hsmainz.pubapp.poi.model.SelectedSearchCriteria;

public class RequestHandlerTest {

	@Test
	public void test() {
		try {
			URL url = new URL("http://localhost:8000/pubapp/poi");
			String request = createRequestString();
			System.out.println(request);
			byte[] requestBytes = request.getBytes(StandardCharsets.UTF_8);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			conn.setRequestProperty("Content-Length", String.valueOf(requestBytes.length));
			conn.setDoOutput(true);
			conn.getOutputStream().write(requestBytes);

			Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

			for (int c; (c = in.read()) >= 0;)
				System.out.print((char) c);

		} catch (Exception e) {
			fail("Failed" + e);
		}

	}

	private String createRequestString() {
		// Initilazie coordinates
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
		Coordinate coordTen = new Coordinate();
		coordTen.setLat(50.001499);
		coordTen.setLng(8.267261);

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
		coords.add(coordTen);

		// Initialize interests
		List<String> interests = new ArrayList<>();
		interests.add("bar");
		interests.add("atm");

		SelectedSearchCriteria criteria = new SelectedSearchCriteria();
		criteria.setCoordinates(coords);
		criteria.setInterests(interests);
		return "callback=" + "xxx" + "&criteria=" + new Gson().toJson(criteria) + "&api=google&searchtype=radius";

	}
}

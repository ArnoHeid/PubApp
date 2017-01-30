package de.hsmainz.pubapp.poi.controller;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

import com.google.gson.Gson;

import de.hsmainz.pubapp.poi.model.PoiToFind;
import de.hsmainz.pubapp.poi.model.PoiSearchResult;
import de.hsmainz.pubapp.poi.model.ResultPoi;


public class PoiService {

	private static final String BASE_API_URL = "http://overpass-api.de/api/interpreter?data=[out:json][timeout:25];";
	private static final String LOG_TAG = "PubApp_PoiSearch";

	private static final String PLACES_API_BASE = "https://maps.googleapis.com/maps/api/place";

	private static final String TYPE_SEARCH = "/search";

	private static final String OUT_JSON = "/json";

	private static final String API_KEY = "AIzaSyDaqvFY5-JfIFPK1e7HdjVi-OYmuc2QPE8";

	public List<ResultPoi> searchForPoiWithRadius(String interest, PoiToFind poi, int radius) {
		String requestStart = buildRequestOverpass(interest, poi.getEndLat(), poi.getEndLng(), 1000);
		
		InputStreamReader in = null;
		in = postQuery(requestStart, in);

		PoiSearchResult placesResult = new Gson().fromJson(in, PoiSearchResult.class);
		List<ResultPoi> places = placesResult.getList();

		if (places.isEmpty()) {
			System.out.println("No POIs found");
		} else {
			for (ResultPoi place : places) {
				System.out.println(place.getTags().getName());
			}
		}

		return places;

	}
	
	public String buildRequestGooglePlaces(String interest, double lat, double lng, int radius) {
		String requestUri = "";
		try {
			StringBuilder sb = new StringBuilder(PLACES_API_BASE);
			sb.append(TYPE_SEARCH);
			sb.append(OUT_JSON);
			sb.append("?sensor=false");
			sb.append("&key=" + API_KEY);
			sb.append("&keyword=" + URLEncoder.encode(interest, "utf8"));
			sb.append("&location=" + Double.toString(lat) + "," + Double.toString(lng));
			sb.append("&radius=" + String.valueOf(radius));
			requestUri = sb.toString();
			System.out.println(requestUri);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return requestUri;
	}
	
	public String buildRequestOverpass(String interest, double lat, double lng, int radius) {
		String requestUri = "";
		String amenityQueryString = "[amenity=" + interest + "]";
		String coordWithRadiusQueryString = "(around:" + radius + "," + lat + "," + lng + ");";
		StringBuilder sb = new StringBuilder(BASE_API_URL);
		sb.append("(node" + amenityQueryString);
		sb.append(coordWithRadiusQueryString);
		sb.append("way" + amenityQueryString);
		sb.append(coordWithRadiusQueryString);
		sb.append("relation" + amenityQueryString);
		sb.append(coordWithRadiusQueryString);
		sb.append(");out;");
		requestUri = sb.toString();
		System.out.println(requestUri);

		return requestUri;
	}

	public InputStreamReader postQuery(String request, InputStreamReader in) {
		URL url = null;
		HttpURLConnection conn;
		try {
			url = new URL(request);
			conn = (HttpURLConnection) url.openConnection();
			in = new InputStreamReader(conn.getInputStream());
			
		} catch (MalformedURLException e) {
			System.out.println(LOG_TAG + "Error processing API URL" + e);
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println(LOG_TAG + "Error connecting API" + e);
			e.printStackTrace();
		}
		System.out.println(in.toString());
		return in;
	}
}
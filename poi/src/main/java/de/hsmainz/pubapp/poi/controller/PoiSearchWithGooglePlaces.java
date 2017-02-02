package de.hsmainz.pubapp.poi.controller;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import de.hsmainz.pubapp.poi.model.Coordinate;
import de.hsmainz.pubapp.poi.model.ResultPoi;
import de.hsmainz.pubapp.poi.model.googleapi.GooglePoiSearchResult;
import de.hsmainz.pubapp.poi.model.googleapi.GoogleResultPoi;

public class PoiSearchWithGooglePlaces implements PoiSearchService {
	// ****************************************
	// CONSTANTS
	// ****************************************
	private static final String LOG_TAG = "PubApp_SearchPoiWithGoogle";
	private static final String PLACES_API_BASE = "https://maps.googleapis.com/maps/api/place";
	private static final String TYPE_SEARCH = "/search";
	private static final String OUT_JSON = "/json";
	private static final String API_KEY = "AIzaSyDaqvFY5-JfIFPK1e7HdjVi-OYmuc2QPE8";

	// ****************************************
	// VARIABLES
	// ****************************************
	public String searchType;

	// ****************************************
	// INIT/CONSTRUCTOR
	// ****************************************

	// ****************************************
	// GETTER/SETTER
	// ****************************************
	@Override
	public void setSearchType(String searchType) {
		this.searchType = searchType;

	}

	@Override
	public String getSearchType() {
		return searchType;
	}

	// ****************************************
	// PUBLIC METHODS
	// ****************************************
	@Override
	public List<ResultPoi> getPoisWithinRadius(String interest, Coordinate coord, int radius) {
		String requestStart = buildRequestRadius(interest, coord.getLat(), coord.getLng(), radius);

		InputStreamReader in = null;
		in = postQuery(requestStart, in);

		GooglePoiSearchResult placesResult = new Gson().fromJson(in, GooglePoiSearchResult.class);
		List<GoogleResultPoi> places = placesResult.getList();

		if (places.isEmpty()) {
			System.out.println("No POIs found");
		}

		return transformApiResultsToResultPoi(places, interest);

	}

	@Override
	public List<ResultPoi> getPoisWithinBBox(String interest, Coordinate[] coords) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
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

	// ****************************************
	// PRIVATE METHODS
	// ****************************************
	/**
	 * Transform API specific Return-Objects to Standard ResultPoi with all
	 * relevant information
	 * 
	 * @param places
	 *            List of all Results that Google API returned saved in
	 *            GoogleResultPoi
	 * @param interest
	 *            of Poi as String
	 * @return all result POIs as List transformed in ResultPoi-Objects
	 */
	private List<ResultPoi> transformApiResultsToResultPoi(List<GoogleResultPoi> places, String interest) {

		List<ResultPoi> results = new ArrayList<ResultPoi>();

		for (GoogleResultPoi googlePoi : places) {
			ResultPoi resultPoi = new ResultPoi();
			resultPoi.setName(googlePoi.getName());
			resultPoi.setInterest(interest);
			resultPoi.setLat(googlePoi.getGeometry().getLocation().getLat());
			resultPoi.setLon(googlePoi.getGeometry().getLocation().getLng());
			results.add(resultPoi);
		}
		return results;

	}

	/**
	 * Create Request String
	 * 
	 * @param interest
	 * @param lat
	 * @param lng
	 * @param radius
	 * @return String of Request-URL that needs to be called according to given
	 *         parameters
	 */
	private String buildRequestRadius(String interest, double lat, double lng, int radius) {
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
			e.printStackTrace();
		}

		return requestUri;
	}

	// *****************************************
	// INNER CLASSES
	// *****************************************
}

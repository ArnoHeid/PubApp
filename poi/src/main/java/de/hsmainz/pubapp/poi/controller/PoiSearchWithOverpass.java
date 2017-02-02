package de.hsmainz.pubapp.poi.controller;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import de.hsmainz.pubapp.poi.model.Coordinate;
import de.hsmainz.pubapp.poi.model.ResultPoi;
import de.hsmainz.pubapp.poi.model.overpass.OverpassPoiSearchResult;
import de.hsmainz.pubapp.poi.model.overpass.OverpassResultPoi;

public class PoiSearchWithOverpass implements PoiSearchService {
	// ****************************************
	// CONSTANTS
	// ****************************************
	private static final String BASE_API_URL = "http://overpass-api.de/api/interpreter?data=[out:json][timeout:25];";
	private static final String LOG_TAG = "PubApp_PoiSearchWithOverpass";

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

		OverpassPoiSearchResult placesResult = new Gson().fromJson(in, OverpassPoiSearchResult.class);
		List<OverpassResultPoi> places = placesResult.getList();

		return transformApiResultsToResultPoi(places);

	}

	@Override
	public List<ResultPoi> getPoisWithinBBox(String interest, Coordinate[] coords) {

		String requestStart = buildRequestBBox(interest, coords);

		InputStreamReader in = null;
		in = postQuery(requestStart, in);

		OverpassPoiSearchResult placesResult = new Gson().fromJson(in, OverpassPoiSearchResult.class);
		List<OverpassResultPoi> places = placesResult.getList();

		return transformApiResultsToResultPoi(places);
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
	 * Create Request String for Radius Search
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

	/**
	 * Create Request String for Bounding Box Search
	 * 
	 * @param interest
	 * @param coords
	 *            array with two Coordinated for Bounding Box edges
	 * @return String of Request-URL that needs to be called according to given
	 *         parameters
	 */
	public String buildRequestBBox(String interest, Coordinate[] coords) {
		String requestUri = "";
		String amenityQueryString = "[amenity=" + interest + "]";
		String bBoxQueryString = "(" + coords[0].getLat() + "," + coords[0].getLng() + "," + coords[1].getLat() + ","
				+ coords[1].getLng() + ");";
		StringBuilder sb = new StringBuilder(BASE_API_URL);
		sb.append("(node" + amenityQueryString);
		sb.append(bBoxQueryString);
		sb.append("way" + amenityQueryString);
		sb.append(bBoxQueryString);
		sb.append("relation" + amenityQueryString);
		sb.append(bBoxQueryString);
		sb.append(");out;");
		requestUri = sb.toString();
		System.out.println(requestUri);

		return requestUri;
	}

	/**
	 * Transform API specific Return-Objects to Standard ResultPoi with all
	 * relevant information
	 * 
	 * @param places
	 *            List of all Results that Overpass-API returned saved in
	 *            OverpassResultPoi
	 * @return all result POIs as List transformed in ResultPoi-Objects
	 */
	private List<ResultPoi> transformApiResultsToResultPoi(List<OverpassResultPoi> places) {

		List<ResultPoi> results = new ArrayList<ResultPoi>();

		for (OverpassResultPoi overpassPoi : places) {
			ResultPoi resultPoi = new ResultPoi();
			resultPoi.setName(overpassPoi.getTags().getName());
			resultPoi.setInterest(overpassPoi.getTags().getAmenity());
			resultPoi.setLat(overpassPoi.getLat());
			resultPoi.setLon(overpassPoi.getLon());
			results.add(resultPoi);
		}
		return results;

	}

	// *****************************************
	// INNER CLASSES
	// *****************************************

}
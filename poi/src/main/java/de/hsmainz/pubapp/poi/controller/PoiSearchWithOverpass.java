package de.hsmainz.pubapp.poi.controller;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;

import org.apache.log4j.Logger;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import de.hsmainz.pubapp.poi.model.Coordinate;
import de.hsmainz.pubapp.poi.model.Details;
import de.hsmainz.pubapp.poi.model.ResultPoi;
import de.hsmainz.pubapp.poi.model.overpass.OverpassPoiSearchResult;
import de.hsmainz.pubapp.poi.model.overpass.OverpassResultPoi;

/**
 * Implementation of PoiSearchService with using Overpass API
 * 
 * @author caro
 *
 */
public class PoiSearchWithOverpass implements PoiSearchService {
	// ****************************************
	// CONSTANTS
	// ****************************************
	private static final Logger logger = Logger.getLogger(PoiSearchWithOverpass.class);
	private static final ResourceBundle lables = ResourceBundle.getBundle("lables", Locale.getDefault());
	private static final String BASE_API_URL = "http://overpass-api.de/api/interpreter?data=[out:json][timeout:25];";

	// ****************************************
	// VARIABLES
	// ****************************************
	private String searchType;

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
	public Set<ResultPoi> getPoisWithinRadius(String interest, Coordinate coord, int radius) {
		String request = buildRequestRadius(interest, coord.getLat(), coord.getLng(), radius);

		InputStreamReader in = postQuery(request);
		List<ResultPoi> resultPois = null;
		try {
			Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
			OverpassPoiSearchResult placesResult = gson.fromJson(in, OverpassPoiSearchResult.class);
			List<OverpassResultPoi> places = placesResult.getList();
			if (places.isEmpty()) {
				logger.info("No POIs found with URL:" + request);
			}
			resultPois = transformApiResultsToResultPoi(places);
		} catch (Exception e) {
			logger.error("Problem while saving POIs in List", e);
		}

		return new HashSet<>(resultPois);
	}

	@Override
	public Set<ResultPoi> getPoisWithinBBox(String interest, Coordinate[] coords) {
		String requestStart = buildRequestBBox(interest, coords);
		InputStreamReader in = postQuery(requestStart);

		OverpassPoiSearchResult placesResult = new Gson().fromJson(in, OverpassPoiSearchResult.class);
		List<OverpassResultPoi> places = placesResult.getList();

		List<ResultPoi> transformedPoiList = transformApiResultsToResultPoi(places);
		return new HashSet<>(transformedPoiList);
	}

	@Override
	public InputStreamReader postQuery(String request) {
		URL url = null;
		HttpURLConnection conn;
		InputStreamReader in = null;
		try {
			url = new URL(request);
			conn = (HttpURLConnection) url.openConnection();
			in = new InputStreamReader(conn.getInputStream());

		} catch (MalformedURLException e) {
			logger.error("Error processing API URL" + e);
		} catch (IOException e) {
			logger.error("Error connecting API" + e);
		}
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
		StringBuilder sb = new StringBuilder(BASE_API_URL);
		String amenityQueryString = "[amenity=" + interest + "]";
		String coordWithRadiusQueryString = "(around:" + radius + "," + lat + "," + lng + ");";
		sb.append("(node" + amenityQueryString);
		sb.append(coordWithRadiusQueryString);
		sb.append("way" + amenityQueryString);
		sb.append(coordWithRadiusQueryString);
		sb.append("relation" + amenityQueryString);
		sb.append(coordWithRadiusQueryString);
		sb.append(");out;");
		String requestUri = sb.toString();
		if (logger.isDebugEnabled()) {
			logger.debug("Request URI: " + requestUri);
		}

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
		String requestUri = sb.toString();
		if (logger.isDebugEnabled()) {
			logger.debug("Request URI: " + requestUri);
		}

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

		List<ResultPoi> results = new ArrayList<>();

		for (OverpassResultPoi overpassPoi : places) {
			ResultPoi resultPoi = new ResultPoi();
			resultPoi.setName(overpassPoi.getTags().getName());
			resultPoi.setInterest(overpassPoi.getTags().getAmenity());
			resultPoi.setLat(overpassPoi.getLat());
			resultPoi.setLon(overpassPoi.getLon());

			Details details = new Details();
			try {
				details.setOpeningHours(overpassPoi.getTags().getOpeningHours());
				details.setIsOpen(lables.getString("message_no_open_now"));
			} catch (NullPointerException e) {
				logger.info(
						e + "No information about opening hours found. Messages are being set as defined for the following place:"
								+ overpassPoi.getTags().getName());
				details.setIsOpen(lables.getString("message_no_open_now"));
				details.setOpeningHours(lables.getString("message_no_opening_hours"));
			}

			resultPoi.setDetails(details);

			results.add(resultPoi);
		}
		return results;

	}

	// *****************************************
	// INNER CLASSES
	// *****************************************

}
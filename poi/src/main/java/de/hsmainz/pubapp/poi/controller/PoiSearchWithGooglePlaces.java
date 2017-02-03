package de.hsmainz.pubapp.poi.controller;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import de.hsmainz.pubapp.poi.model.Coordinate;
import de.hsmainz.pubapp.poi.model.Details;
import de.hsmainz.pubapp.poi.model.ResultPoi;
import de.hsmainz.pubapp.poi.model.googleapi.GooglePoiSearchResult;
import de.hsmainz.pubapp.poi.model.googleapi.GoogleResultPoi;

/**
 * Implementation of PoiSearchService with using Overpass API
 * 
 * @author caro
 *
 */
public class PoiSearchWithGooglePlaces implements PoiSearchService {
	// ****************************************
	// CONSTANTS
	// ****************************************
	private static final Logger logger = Logger.getLogger(PoiSearchWithGooglePlaces.class);
	private static final ResourceBundle config = ResourceBundle.getBundle("config");
	private static final ResourceBundle lables = ResourceBundle.getBundle("lables", Locale.getDefault());

	private static final String PLACES_API_BASE = "https://maps.googleapis.com/maps/api/place";
	private static final String TYPE_SEARCH = "/search";
	private static final String OUT_JSON = "/json";
	private static final String API_KEY = config.getString("api_key_google");

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
	public List<ResultPoi> getPoisWithinRadius(String interest, Coordinate coord, int radius) {
		String request = buildRequestRadius(interest, coord.getLat(), coord.getLng(), radius);

		InputStreamReader in = postQuery(request);
		List<ResultPoi> resultPois = null;

		try {
			Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
			GooglePoiSearchResult placesResult = gson.fromJson(in, GooglePoiSearchResult.class);
			List<GoogleResultPoi> places = placesResult.getList();
			if (places.isEmpty()) {
				logger.info("No POIs found with URL:" + request);
			}
			resultPois = transformApiResultsToResultPoi(places, interest);
		} catch (Exception e) {
			logger.error("Problem while saving POIs in List", e);

		}

		return resultPois;

	}

	@Override
	public List<ResultPoi> getPoisWithinBBox(String interest, Coordinate[] coords) {
		logger.warn("Tried to do BoundingBox Search with Google Places API with: " + interest + " and "
				+ Arrays.toString(coords));
		return new ArrayList<>();
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

		List<ResultPoi> results = new ArrayList<>();

		for (GoogleResultPoi googlePoi : places) {
			ResultPoi resultPoi = new ResultPoi();
			resultPoi.setName(googlePoi.getName());
			resultPoi.setInterest(interest);
			resultPoi.setLat(googlePoi.getGeometry().getLocation().getLat());
			resultPoi.setLon(googlePoi.getGeometry().getLocation().getLng());
			Details details = new Details();
			try {
				details.setIsOpen(Boolean.toString(googlePoi.getOpeningHours().getOpenNow()));
				details.setOpeningHours(lables.getString("message_no_opening_hours"));
			} catch (NullPointerException e) {
				logger.info(
						e + "No information about opening hours found. Messages are being set as defined for the following place:"
								+ googlePoi.getName());
				details.setIsOpen(lables.getString("message_no_open_now"));
				details.setOpeningHours(lables.getString("message_no_opening_hours"));
			}
			resultPoi.setDetails(details);
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
			sb.append("&radius=" + Integer.toString(radius));
			requestUri = sb.toString();
			if (logger.isDebugEnabled()) {
				logger.debug("Request URI: " + requestUri);
			}
		} catch (UnsupportedEncodingException e) {
			logger.error(e, e);
		}

		return requestUri;
	}

	// *****************************************
	// INNER CLASSES
	// *****************************************
}

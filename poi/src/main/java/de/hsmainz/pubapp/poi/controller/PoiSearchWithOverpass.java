package de.hsmainz.pubapp.poi.controller;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import de.hsmainz.pubapp.poi.model.PoiBoundingBox;
import de.hsmainz.pubapp.poi.model.ResultPoi;
import de.hsmainz.pubapp.poi.model.overpass.OverpassPoiSearchResult;
import de.hsmainz.pubapp.poi.model.overpass.OverpassResultPoi;

public class PoiSearchWithOverpass implements PoiSearchService {

	private static final String BASE_API_URL = "http://overpass-api.de/api/interpreter?data=[out:json][timeout:25];";
	private static final String LOG_TAG = "PubApp_PoiSearchWithOverpass";

	@Override
	public List<ResultPoi> getPoisWithinRadius(String interest, PoiBoundingBox poi, int radius) {
		String requestStart = buildRequestRadius(interest, poi.getStartLat(), poi.getStartLng(), 1000);

		InputStreamReader in = null;
		in = postQuery(requestStart, in);

		OverpassPoiSearchResult placesResult = new Gson().fromJson(in, OverpassPoiSearchResult.class);
		List<OverpassResultPoi> places = placesResult.getList();

		return transformApiResultsToResultPoi(places);

	}

	@Override
	public List<ResultPoi> getPoisWithinBBox(String interest, PoiBoundingBox poi) {

		String requestStart = buildRequestBBox(interest, poi);

		InputStreamReader in = null;
		in = postQuery(requestStart, in);

		OverpassPoiSearchResult placesResult = new Gson().fromJson(in, OverpassPoiSearchResult.class);
		List<OverpassResultPoi> places = placesResult.getList();

		return transformApiResultsToResultPoi(places);
	}

	public String buildRequestBBox(String interest, PoiBoundingBox poi) {
		String requestUri = "";
		String amenityQueryString = "[amenity=" + interest + "]";
		String bBoxQueryString = "(" + poi.getStartLat() + "," + poi.getStartLng() + "," + poi.getEndLat() + ","
				+ poi.getEndLng() + ");";
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

	@Override
	public String buildRequestRadius(String interest, double lat, double lng, int radius) {
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

}
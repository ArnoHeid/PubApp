package de.hsmainz.pubapp.poi.controller;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.logging.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import de.hsmainz.pubapp.poi.model.Place;

public class PoiService {
	private static final String LOG_TAG = "PubApp_PoiSearch";

	private static final String PLACES_API_BASE = "https://maps.googleapis.com/maps/api/place";

	// private static final String TYPE_AUTOCOMPLETE = "/autocomplete";
	//private static final String TYPE_DETAILS = "/details";
	private static final String TYPE_SEARCH = "/search";

	private static final String OUT_JSON = "/json";

	// KEY!
	private static final String API_KEY = "*****";

	// public static ArrayList<Place> autocomplete(String input) {
	// ArrayList<Place> resultList = null;
	//
	// HttpURLConnection conn = null;
	// StringBuilder jsonResults = new StringBuilder();
	// try {
	// StringBuilder sb = new StringBuilder(PLACES_API_BASE);
	// sb.append(TYPE_AUTOCOMPLETE);
	// sb.append(OUT_JSON);
	// sb.append("?sensor=false");
	// sb.append("&key=" + API_KEY);
	// sb.append("&input=" + URLEncoder.encode(input, "utf8"));
	//
	// URL url = new URL(sb.toString());
	// conn = (HttpURLConnection) url.openConnection();
	// InputStreamReader in = new InputStreamReader(conn.getInputStream());
	//
	// int read;
	// char[] buff = new char[1024];
	// while ((read = in.read(buff)) != -1) {
	// jsonResults.append(buff, 0, read);
	// }
	// } catch (MalformedURLException e) {
	// System.out.print(LOG_TAG + "Error processing Places API URL" + e);
	// return resultList;
	// } catch (IOException e) {
	// System.out.print(LOG_TAG + "Error connecting to Places API" + e);
	// return resultList;
	// } finally {
	// if (conn != null) {
	// conn.disconnect();
	// }
	// }
	//
	// try {
	// // Create a JSON object hierarchy from the results
	// JsonObject jsonObj = new JsonObject(jsonResults.toString());
	// JsonArray predsJsonArray = jsonObj.getAsJsonArray("predictions");
	//
	// // Extract the Place descriptions from the results
	// resultList = new ArrayList<Place>(predsJsonArray.length());
	// for (int i = 0; i < predsJsonArray.length(); i++) {
	// Place place = new Place();
	// place.reference = predsJsonArray.getJSONObject(i).getString("reference");
	// place.name = predsJsonArray.getJSONObject(i).getString("description");
	// resultList.add(place);
	// }
	// } catch (JSONException e) {
	// Log.e(LOG_TAG, "Error processing JSON results", e);
	// }
	//
	// return resultList;
	// }

	public static ArrayList<Place> search(String keyword, double lat, double lng, int radius) {
		ArrayList<Place> resultList = null;

		HttpURLConnection conn = null;
		//StringBuilder jsonResults = new StringBuilder();
		try {
			StringBuilder sb = new StringBuilder(PLACES_API_BASE);
			sb.append(TYPE_SEARCH);
			sb.append(OUT_JSON);
			sb.append("?sensor=false");
			sb.append("&key=" + API_KEY);
			sb.append("&keyword=" + URLEncoder.encode(keyword, "utf8"));
			sb.append("&location=" + String.valueOf(lat) + "," + String.valueOf(lng));
			sb.append("&radius=" + String.valueOf(radius));
			
			System.out.println(sb.toString());
			
			URL url = new URL(sb.toString());
			conn = (HttpURLConnection) url.openConnection();
			InputStreamReader in = new InputStreamReader(conn.getInputStream());
			System.out.println(in.toString());
			
			Gson gson = new GsonBuilder().create();
            Place place = gson.fromJson(in, Place.class);
            System.out.println(place.getName());
//			JSONObject jsonObj = new JSONObject(jsonResults.toString());
//			JSONArray predsJsonArray = jsonObj.getJSONArray("results");
//
//			// Extract the Place descriptions from the results
//			resultList = new ArrayList<Place>(predsJsonArray.length());
//			for (int i = 0; i < predsJsonArray.length(); i++) {
//				Place place = new Place();
//				place.reference = predsJsonArray.getJSONObject(i).getString("reference");
//				place.name = predsJsonArray.getJSONObject(i).getString("name");
//				resultList.add(place);
//			}

		return resultList;
		
//			int read;
//			char[] buff = new char[1024];
//			while ((read = in.read(buff)) != -1) {
//				jsonResults.append(buff, 0, read);
//			}
		} catch (MalformedURLException e) {
			System.out.println(LOG_TAG + "Error processing Places API URL" + e);
			return resultList;
		} catch (IOException e) {
			System.out.println(LOG_TAG + "Error connecting to Places API" + e);
			return resultList;
		} finally {
			if (conn != null) {
				conn.disconnect();
			}
		}

//		try {
//			Gson gson = new GsonBuilder().create();
//            Place place = gson.fromJson(in, Place.class);
//            System.out.println(p);
//			// Create a JSON object hierarchy from the results
//			JSONObject jsonObj = new JSONObject(jsonResults.toString());
//			JSONArray predsJsonArray = jsonObj.getJSONArray("results");
//
//			// Extract the Place descriptions from the results
//			resultList = new ArrayList<Place>(predsJsonArray.length());
//			for (int i = 0; i < predsJsonArray.length(); i++) {
//				Place place = new Place();
//				place.reference = predsJsonArray.getJSONObject(i).getString("reference");
//				place.name = predsJsonArray.getJSONObject(i).getString("name");
//				resultList.add(place);
//			}
//		} catch (JSONException e) {
//			Log.e(LOG_TAG, "Error processing JSON results", e);
//		}
//
//		return resultList;
	}

	// public static Place details(String reference) {
	// HttpURLConnection conn = null;
	// StringBuilder jsonResults = new StringBuilder();
	// try {
	// StringBuilder sb = new StringBuilder(PLACES_API_BASE);
	// sb.append(TYPE_DETAILS);
	// sb.append(OUT_JSON);
	// sb.append("?sensor=false");
	// sb.append("&key=" + API_KEY);
	// sb.append("&reference=" + URLEncoder.encode(reference, "utf8"));
	//
	// URL url = new URL(sb.toString());
	// conn = (HttpURLConnection) url.openConnection();
	// InputStreamReader in = new InputStreamReader(conn.getInputStream());
	//
	// // Load the results into a StringBuilder
	// int read;
	// char[] buff = new char[1024];
	// while ((read = in.read(buff)) != -1) {
	// jsonResults.append(buff, 0, read);
	// }
	// } catch (MalformedURLException e) {
	// Log.e(LOG_TAG, "Error processing Places API URL", e);
	// return null;
	// } catch (IOException e) {
	// Log.e(LOG_TAG, "Error connecting to Places API", e);
	// return null;
	// } finally {
	// if (conn != null) {
	// conn.disconnect();
	// }
	// }
	//
	// Place place = null;
	// try {
	// // Create a JSON object hierarchy from the results
	// JSONObject jsonObj = new
	// JSONObject(jsonResults.toString()).getJSONObject("result");
	//
	// place = new Place();
	// place.icon = jsonObj.getString("icon");
	// place.name = jsonObj.getString("name");
	// place.formatted_address = jsonObj.getString("formatted_address");
	// if (jsonObj.has("formatted_phone_number")) {
	// place.formatted_phone_number =
	// jsonObj.getString("formatted_phone_number");
	// }
	// } catch (JSONException e) {
	// Log.e(LOG_TAG, "Error processing JSON results", e);
	// }
	//
	// return place;
	// }
}
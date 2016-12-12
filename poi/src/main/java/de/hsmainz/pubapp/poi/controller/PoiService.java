package de.hsmainz.pubapp.poi.controller;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import de.hsmainz.pubapp.poi.model.Result;
import de.hsmainz.pubapp.poi.model.Location;
import de.hsmainz.pubapp.poi.model.Place;
import de.hsmainz.pubapp.poi.model.PlacesResult;

import java.lang.reflect.Type;
public class PoiService {
	private static final String LOG_TAG = "PubApp_PoiSearch";

	private static final String PLACES_API_BASE = "https://maps.googleapis.com/maps/api/place";

	private static final String TYPE_SEARCH = "/search";

	private static final String OUT_JSON = "/json";

	private static final String API_KEY = "AIzaSyDaqvFY5-JfIFPK1e7HdjVi-OYmuc2QPE8";
	
	

	public static ArrayList<Place> search(String keyword, Location location, int radius) {
		ArrayList<Place> resultList = null;

		HttpURLConnection conn = null;
		try {
			StringBuilder sb = new StringBuilder(PLACES_API_BASE);
			sb.append(TYPE_SEARCH);
			sb.append(OUT_JSON);
			sb.append("?sensor=false");
			sb.append("&key=" + API_KEY);
			sb.append("&keyword=" + URLEncoder.encode(keyword, "utf8"));
			sb.append("&location=" + String.valueOf(location.getLat()) + "," + String.valueOf(location.getLng()));
			sb.append("&radius=" + String.valueOf(radius));
			
			System.out.println(sb.toString());
			
			URL url = new URL(sb.toString());
			conn = (HttpURLConnection) url.openConnection();
			InputStreamReader in = new InputStreamReader(conn.getInputStream());
			System.out.println(in.toString());
			
			Gson gson = new GsonBuilder().create();
			Type listType = new TypeToken<ArrayList<Place>>(){}.getType();

			PlacesResult placesResult = new Gson().fromJson(in, PlacesResult.class);
			List<Place> places = placesResult.getList();
			
			if (places.isEmpty()) {
				System.out.println("No POIs found");
			} else {
				for (Place place: places) {
					System.out.println(place.getName());
				}
			}
          

		return resultList;
		
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
	
		
	}
	
	public void returnSearchResult(List<Place> relevantPois) {
		//POIS in JSON and send to Client
	}
}
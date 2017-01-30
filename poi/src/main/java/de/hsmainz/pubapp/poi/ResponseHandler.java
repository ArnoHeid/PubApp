package de.hsmainz.pubapp.poi;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import de.hsmainz.pubapp.poi.model.GeoJsonFeature;
import de.hsmainz.pubapp.poi.model.GeoJsonFeature.GeoJsonGeometry;
import de.hsmainz.pubapp.poi.model.GeoJsonFeature.GeoJsonProperties;
import de.hsmainz.pubapp.poi.model.GeoJsonFeatureCollection;
import de.hsmainz.pubapp.poi.model.ResultPoi;

public class ResponseHandler {
	
	public String getResponse(List<ResultPoi> allPois) {
		
		List<GeoJsonFeature> features = new ArrayList<GeoJsonFeature>();
		for (ResultPoi place : allPois) {
			//set coordinates for GeoJsonGeometry
			double[] coordinates = new double[2];
	        coordinates[1] = place.getLat();
	        coordinates[0] = place.getLon();
			GeoJsonFeature currentFeature = new GeoJsonFeature("Feature", new GeoJsonProperties(place.getName()), new GeoJsonGeometry("Point", coordinates));
			features.add(currentFeature);
		}
		
		GeoJsonFeatureCollection featureCollection = new GeoJsonFeatureCollection("FeatureCollection", features);		
		Gson gson = new Gson();
		String jsonInString = gson.toJson(featureCollection);
		
		return jsonInString;
	}
	
}

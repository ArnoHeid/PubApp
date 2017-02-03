package de.hsmainz.pubapp.poi.web;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import de.hsmainz.pubapp.poi.model.GeoJsonFeature;
import de.hsmainz.pubapp.poi.model.GeoJsonFeature.GeoJsonGeometry;
import de.hsmainz.pubapp.poi.model.GeoJsonFeature.GeoJsonProperties;
import de.hsmainz.pubapp.poi.model.GeoJsonFeatureCollection;
import de.hsmainz.pubapp.poi.model.ResultPoi;
import de.hsmainz.pubapp.poi.model.ReturnError;

public class ResponseHandler {
	// ****************************************
	// CONSTANTS
	// ****************************************

	// ****************************************
	// VARIABLES
	// ****************************************

	// ****************************************
	// INIT/CONSTRUCTOR
	// ****************************************

	// ****************************************
	// GETTER/SETTER
	// ****************************************

	// ****************************************
	// PUBLIC METHODS
	// ****************************************
	/**
	 * Generate successful response as GeoJSON
	 * 
	 * @param allPois
	 *            List of all POIs found
	 * @return GeoJSON as String with all POIs found
	 */
	public String getResponse(List<ResultPoi> allPois) {

		List<GeoJsonFeature> features = new ArrayList<GeoJsonFeature>();
		for (ResultPoi place : allPois) {
			// set coordinates for GeoJsonGeometry
			double[] coordinates = new double[2];
			coordinates[1] = place.getLat();
			coordinates[0] = place.getLon();
			GeoJsonFeature currentFeature = null;

			try { 
				currentFeature = new GeoJsonFeature("Feature", new GeoJsonProperties(place.getName(),
						place.getInterest(), place.getDetails().getOpeningHours(), place.getDetails().getIsOpen()),
						new GeoJsonGeometry("Point", coordinates));
				features.add(currentFeature);

			} catch (Exception e) {
				System.out.println(e);
			}
		}

		GeoJsonFeatureCollection featureCollection = new GeoJsonFeatureCollection("FeatureCollection", features);
		return new Gson().toJson(featureCollection);
	}

	/**
	 * Generate response for failed request
	 * 
	 * @param errorMessage
	 *            gives details about what went wrong
	 * @return JSOON String with error message
	 */
	public String getErrorResponse(String errorMessage) {
		ReturnError error = new ReturnError("Error", errorMessage);

		return new Gson().toJson(error);

	}

	// ****************************************
	// PRIVATE METHODS
	// ****************************************

	// *****************************************
	// INNER CLASSES
	// *****************************************

}

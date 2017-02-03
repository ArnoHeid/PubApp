package de.hsmainz.pubapp.poi.web;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.google.gson.Gson;

import de.hsmainz.pubapp.poi.model.GeoJsonFeature;
import de.hsmainz.pubapp.poi.model.GeoJsonFeature.GeoJsonGeometry;
import de.hsmainz.pubapp.poi.model.GeoJsonFeature.GeoJsonProperties;
import de.hsmainz.pubapp.poi.model.GeoJsonFeatureCollection;
import de.hsmainz.pubapp.poi.model.ResultPoi;
import de.hsmainz.pubapp.poi.model.ReturnError;

/**
 * Class for generating Response JSON String for either a valid GeoJSON
 * containing all POIs found or a adequate error Message as JSON
 * 
 * @author caro
 *
 */
public class ResponseHandler {
	// ****************************************
	// CONSTANTS
	// ****************************************
	private static final Logger logger = Logger.getLogger(ResponseHandler.class);
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

		List<GeoJsonFeature> features = new ArrayList<>();
		for (ResultPoi place : allPois) {
			// set coordinates for GeoJsonGeometry
			double[] coordinates = new double[2];
			coordinates[1] = place.getLat();
			coordinates[0] = place.getLon();
			GeoJsonFeature currentFeature = null;

			try {
				currentFeature = new GeoJsonFeature("Feature",
						new GeoJsonProperties(place.getName(), place.getInterest(),
								place.getDetails().getOpeningHours(), place.getDetails().getIsOpen()),
						new GeoJsonGeometry("Point", coordinates));
				features.add(currentFeature);

			} catch (NullPointerException e) {
				logger.error("Creating response as GeoJson failed: ", e);
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
		ReturnError error = null;
		try {
			error = new ReturnError("Error", errorMessage);
		} catch (NullPointerException e) {
			logger.error("Creating Response ErrorJson failed:", e);
		}
		logger.info("Error message returned to Client:" + errorMessage);
		return new Gson().toJson(error);

	}

	// ****************************************
	// PRIVATE METHODS
	// ****************************************

	// *****************************************
	// INNER CLASSES
	// *****************************************

}

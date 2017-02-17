package de.hsmainz.pubapp.poi.web;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.ws.rs.FormParam;
//import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import com.google.gson.Gson;

import de.hsmainz.pubapp.poi.controller.PoiSearchInputController;
import de.hsmainz.pubapp.poi.controller.PoiSearchService;
import de.hsmainz.pubapp.poi.controller.PoiSearchWithGooglePlaces;
import de.hsmainz.pubapp.poi.controller.PoiSearchWithOverpass;
import de.hsmainz.pubapp.poi.model.ResultPoi;
import de.hsmainz.pubapp.poi.model.SelectedSearchCriteria;

/**
 * This Class handles requests sent to API. Given routing Coordinates or
 * BoundingBox Coordinates and Interests will be processed. Data for POIs is
 * generated accordingly
 * 
 * @author caro
 *
 */
@Path("poi")
public class RequestHandler {
	// ****************************************
	// CONSTANTS
	// ****************************************
	private final Logger logger = Logger.getLogger(RequestHandler.class);
	private final ResourceBundle config = ResourceBundle.getBundle("config");
	private final ResourceBundle lables = ResourceBundle.getBundle("lables", Locale.getDefault());
	private final String bboxString = config.getString("bounding_box_search");
	private final String radiusString = config.getString("radius_search");
	// ****************************************
	// VARIABLES
	// ****************************************
	private PoiSearchService poiSearchService;
	private String errorMessage;

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
	 * Handles Client GET Request
	 * 
	 * @param callback
	 *            Client can use this field for callback parameters
	 * @param selectedSearchCriteria
	 *            Stores two Lists in JSON Format including all selected
	 *            interests and all given coordinated
	 * @param api
	 *            Defines which API should be used to search for POIs
	 * @param searchType
	 *            Defines which kind of search should be done
	 * @return success: all POIs found including details like their name or
	 *         opening hours; fail: error message according to failure
	 * @throws IOException
	 */
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	public String get(@FormParam("callback") String callback, @FormParam("criteria") String selectedSearchCriteria,
			@FormParam("api") String api, @FormParam("searchtype") String searchType) throws InvocationTargetException {

		logger.debug("MicroService POST method called: " + "Search Criteria given: " + selectedSearchCriteria
				+ "API to use: " + api + "Given search type: " + searchType);

		// Define needed Parameters for Response
		ResponseHandler responseHandler = new ResponseHandler();
		String response = null;
		List<ResultPoi> allPois;
		PoiSearchInputController poiInputController = new PoiSearchInputController();
		SelectedSearchCriteria criteria = new Gson().fromJson(selectedSearchCriteria, SelectedSearchCriteria.class);
		// Validate input parameters and handle request
		try {
			if (!valid(criteria, api, searchType)) {
				response = responseHandler.getErrorResponse(errorMessage);
			} else {
				allPois = poiInputController.getPoisForCriteria(criteria, poiSearchService);
				response = responseHandler.getResponse(allPois);
			}
		} catch (IOException e) {
			logger.error(e);
		}

		return addCallback(callback, response);
	}

	/**
	 * Adds callback to response JSON string
	 * 
	 * @param callback
	 *            String defined by client
	 * @param response
	 *            JSON Response String according to request
	 * @return
	 */
	public String addCallback(String callback, String response) {
		if (callback == null || callback.isEmpty()) {
			return response;
		} else {
			return callback + "(" + response + ")";
		}

	}

	// ****************************************
	// PRIVATE METHODS
	// ****************************************
	/**
	 * Validate Client Request
	 * 
	 * @return boolean whether Input is valid
	 */
	private boolean valid(SelectedSearchCriteria criteria, String api, String searchType) throws IOException {
		boolean valid = true;

		if (criteria != null) {
			if (bboxString.equalsIgnoreCase(searchType) || radiusString.equals(searchType)) {
				criteria.setSearchType(searchType);
			} else {
				criteria.setSearchType(config.getString("standard_search_type"));
			}
			if (config.getString("google_places_api").equalsIgnoreCase(api)
					|| config.getString("overpass_api").equalsIgnoreCase(api)) {
				criteria.setApi(api);
			} else {
				criteria.setApi(config.getString("standard_api"));
			}

			if (criteria.getInterests() == null || criteria.getInterests().isEmpty()) {
				errorMessage = lables.getString("error_no_interest");
			}

			setPoiSearchService(criteria);

		} else {
			errorMessage = lables.getString("error_criteria");
		}
		if (!(errorMessage == null || errorMessage.isEmpty())) {
			valid = false;
		}
		return valid;
	}

	private void setPoiSearchService(SelectedSearchCriteria criteria) {
		if (bboxString.equals(criteria.getSearchType())) {
			if (criteria.getCoordinates().size() != 2) {
				errorMessage = lables.getString("error_bbox_amout_coords");
			}
			poiSearchService = new PoiSearchWithOverpass();
			poiSearchService.setSearchType(criteria.getSearchType());
		} else if (radiusString.equals(criteria.getSearchType())) {
			if (config.getString("google_places_api").equals(criteria.getApi())) {
				poiSearchService = new PoiSearchWithGooglePlaces();
			} else {
				poiSearchService = new PoiSearchWithOverpass();
			}
			poiSearchService.setSearchType(config.getString("radius_search"));
		}

	}

	// *****************************************
	// INNER CLASSES
	// *****************************************

}

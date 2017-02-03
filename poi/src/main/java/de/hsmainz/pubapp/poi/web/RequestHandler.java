package de.hsmainz.pubapp.poi.web;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
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
	private static final Logger logger = Logger.getLogger(RequestHandler.class);
	// ****************************************
	// VARIABLES
	// ****************************************
	private PoiSearchService poiSearchService;
	private String errorMessage;
	private ResourceBundle config = ResourceBundle.getBundle("config");
	private ResourceBundle lables = ResourceBundle.getBundle("lables", Locale.getDefault());

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
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String get(@QueryParam("callback") String callback, @QueryParam("criteria") String selectedSearchCriteria,
			@QueryParam("api") String api, @QueryParam("searchtype") String searchType)
			throws IOException, InvocationTargetException {
		logger.debug("MicroService GET method called: " + "Search Criteria given: " + selectedSearchCriteria
				+ "API to use: " + api + "Given search type: " + searchType);
		// Define needed Parameters for Response
		ResponseHandler responseHandler = new ResponseHandler();
		String response;
		List<ResultPoi> allPois;
		PoiSearchInputController poiInputController = new PoiSearchInputController();
		SelectedSearchCriteria criteria = null;
		criteria = new Gson().fromJson(selectedSearchCriteria, SelectedSearchCriteria.class);
		// Validate input parameters and handle request
		if (!valid(criteria, api, searchType)) {
			response = responseHandler.getErrorResponse(errorMessage);
		} else {
			allPois = poiInputController.getPoisForCriteria(criteria, poiSearchService);
			response = responseHandler.getResponse(allPois);
		}

		return addCallback(callback, response);
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
		if (searchType == null)
			searchType = config.getString("standard_search_type");
		if (api == null)
			api = config.getString("standard_api");
		if (criteria != null) {
			if (config.getString("bounding_box_search").equals(searchType)) {
				if (criteria.getCoordinates().size() != 2) {
					errorMessage = lables.getString("error_bbox_amout_coords");
					valid = false;
				}
				poiSearchService = new PoiSearchWithOverpass();
				poiSearchService.setSearchType(searchType);
			} else if (config.getString("radius_search").equals(searchType)) {
				if (config.getString("google_places_api").equals(api)) {
					poiSearchService = new PoiSearchWithGooglePlaces();
				} else {
					poiSearchService = new PoiSearchWithOverpass();
				}
				poiSearchService.setSearchType(searchType);
			}
			if (criteria.getInterests() == null || criteria.getInterests().isEmpty()) {
				errorMessage = lables.getString("error_no_interest");
				valid = false;
			}
		} else {
			errorMessage = lables.getString("error_criteria");
			valid = false;
		}
		return valid;
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
	// *****************************************
	// INNER CLASSES
	// *****************************************

}

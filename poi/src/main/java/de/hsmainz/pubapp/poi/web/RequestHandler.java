package de.hsmainz.pubapp.poi.web;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import com.google.gson.Gson;

import de.hsmainz.pubapp.poi.controller.PoiSearchInputController;
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
	@Consumes("application/x-www-form-urlencoded")
	@Produces(MediaType.TEXT_PLAIN)
	public String post(@FormParam("callback") String callback, @FormParam("criteria") String selectedSearchCriteria,
			@FormParam("api") String api, @FormParam("searchtype") String searchType) throws InvocationTargetException {

		logger.debug("MicroService POST method called: " + "Search Criteria given: " + selectedSearchCriteria
				+ "API to use: " + api + "Given search type: " + searchType);

		// Define needed Parameters for Response
		ResponseHandler responseHandler = new ResponseHandler();

		SelectedSearchCriteria criteria = new Gson().fromJson(selectedSearchCriteria, SelectedSearchCriteria.class);
		PoiSearchInputController poiInputController = new PoiSearchInputController();
		String errorMessage = poiInputController.validateInput(criteria, searchType);

		// generate response for Client
		String response;
		if (errorMessage == null || errorMessage.isEmpty()) {
			response = responseHandler.getResponse(poiInputController.getPoisForCriteria(criteria, searchType, api));
		} else {
			response = responseHandler.getErrorResponse(errorMessage);
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

	// *****************************************
	// INNER CLASSES
	// *****************************************

}

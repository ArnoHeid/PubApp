package de.hsmainz.pubapp.poi.web;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javax.ws.rs.FormParam;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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
	 * @param <HttpServletResponse>
	 * 
	 * @param origin
	 *            Client sends information about origin in header
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
	@Produces(MediaType.APPLICATION_JSON)
	public Response post(@HeaderParam("origin") String origin, @FormParam("criteria") String selectedSearchCriteria,
			@FormParam("api") String api, @FormParam("searchtype") String searchType) throws InvocationTargetException {

		logger.debug("MicroService POST method called: " + "Search Criteria given: " + selectedSearchCriteria
				+ "API to use: " + api + "Given search type: " + searchType);

		// Define needed Parameters for Response
		ResponseHandler responseHandler = new ResponseHandler();
		SelectedSearchCriteria criteria = new Gson().fromJson(selectedSearchCriteria, SelectedSearchCriteria.class);
		PoiSearchInputController poiInputController = new PoiSearchInputController();
		String errorMessage = poiInputController.validateInput(criteria, searchType);

		// Generate response for Client
		String responseBody;
		if (errorMessage == null || errorMessage.isEmpty()) {
			responseBody = responseHandler
					.getResponse(poiInputController.getPoisForCriteria(criteria, searchType, api));
		} else {
			responseBody = responseHandler.getErrorResponse(errorMessage);
		}

		return Response.status(200).entity(responseBody).header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
				.header("Access-Control-Allow-Credentials", true).build();
	}

	// ****************************************
	// PRIVATE METHODS
	// ****************************************

	// *****************************************
	// INNER CLASSES
	// *****************************************

}

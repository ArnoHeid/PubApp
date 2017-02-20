package de.hsmainz.pubapp.poi.web;

import java.lang.reflect.InvocationTargetException;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;

import de.hsmainz.pubapp.poi.controller.PoiSearchInputController;
import de.hsmainz.pubapp.poi.model.SelectedSearchCriteria;
import org.apache.log4j.Logger;

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

	private static final Logger logger = Logger.getLogger(PoiSearchInputController.class);

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
	 * @param request
	 *            all search criteria given by client including coordinates,
	 *            interest and API and search type details
	 * @return
	 * @throws InvocationTargetException
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response post(String request) throws InvocationTargetException {
		// Define needed Parameters for Response
		ResponseHandler responseHandler = new ResponseHandler();
		SelectedSearchCriteria criteria = new Gson().fromJson(request, SelectedSearchCriteria.class);
		PoiSearchInputController poiInputController = new PoiSearchInputController();
		String searchType = criteria.getSearchType();
		String api = criteria.getApi();

		String errorMessage = poiInputController.validateInput(criteria, searchType);

		// Generate response for Client
		String responseBody;
		if (errorMessage == null || errorMessage.isEmpty()) {
			try {
				responseBody = responseHandler
						.getResponse(poiInputController.getPoisForCriteria(criteria, searchType, api));
			}catch (NullPointerException e){
				logger.error("",e);
				responseBody = "Error";
			}

		} else {
			responseBody = responseHandler.getErrorResponse(errorMessage);
		}
		System.setProperty("sun.net.http.allowRestrictedHeaders", "true");
		return Response.status(200).entity(responseBody).build();
	}
	// ****************************************
	// PRIVATE METHODS
	// ****************************************

	// *****************************************
	// INNER CLASSES
	// *****************************************

}

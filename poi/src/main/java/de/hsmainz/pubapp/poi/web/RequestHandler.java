package de.hsmainz.pubapp.poi.web;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;

import de.hsmainz.pubapp.poi.controller.PoiSearchInputController;
import de.hsmainz.pubapp.poi.controller.PoiSearchService;
import de.hsmainz.pubapp.poi.controller.PoiSearchWithGooglePlaces;
import de.hsmainz.pubapp.poi.controller.PoiSearchWithOverpass;
import de.hsmainz.pubapp.poi.model.ResultPoi;
import de.hsmainz.pubapp.poi.model.SelectedSearchCriteria;

/**
 * This Class handles requests sent to API Given routing Coordinates or
 * BoundingBox Coordinates and Interests will be processed. Data for POIs is
 * generated accordingly
 * 
 * @author caro
 *
 */
@Path("poi")
public class RequestHandler {

	PoiSearchService poiSearchService;
	String errorMessage;
	String standardApi = "google";
	String standardSerachType = "radius";

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String get(@QueryParam("callback") String callback, @QueryParam("criteria") String selectedSearchCriteria,
			@QueryParam("api") String api, @QueryParam("searchtype") String searchType) {

		// Define needed Parameters for Response
		ResponseHandler responseHandler = new ResponseHandler();
		String response = "";
		List<ResultPoi> allPois = new ArrayList<ResultPoi>();
		PoiSearchInputController poiInputController = new PoiSearchInputController();

		// Transform Criteria JSON to Criteria Object
		SelectedSearchCriteria criteria = new Gson().fromJson(selectedSearchCriteria, SelectedSearchCriteria.class);

		// Validate input parameters and handle request
		if (!valid(criteria, api, searchType)) {
			response = responseHandler.getErrorResponse(errorMessage);
		} else {
			allPois = poiInputController.getPoisForCriteria(criteria, poiSearchService);
			response = responseHandler.getResponse(allPois);
		}

		return addCallback(callback, response);
	}

	private boolean valid(SelectedSearchCriteria criteria, String api, String searchType) {

		boolean valid = true;

		if (searchType == null) {
			searchType = standardSerachType;
		}

		if (api == null) {
			api = standardApi;
		}

		if (criteria != null) {
			if ("bbox".equals(searchType)) {
				if (criteria.getCoordinates().size() != 2) {
					errorMessage = "Not enough coordinates selected for Bounding Box Search";
					valid = false;
				}
				poiSearchService = new PoiSearchWithOverpass();
				poiSearchService.setSearchType(searchType);
			} else if ("radius".equals(searchType)) {
				if ("google".equals(api)) {
					poiSearchService = new PoiSearchWithGooglePlaces();
				} else {
					poiSearchService = new PoiSearchWithOverpass();
				}
				poiSearchService.setSearchType(searchType);
			} else if (criteria.getInterests().size() < 1) {
				errorMessage = "No Interest Selected";
				valid = false;
			}
		} else {
			errorMessage = "No search criteria could be proccesed";
			valid = false;
		}
		return valid;
	}

	public String addCallback(String callback, String response) {
		if (callback.isEmpty() || callback == null) {
			return response;
		} else {
			return callback + "(" + response + ")";
		}

	}
}

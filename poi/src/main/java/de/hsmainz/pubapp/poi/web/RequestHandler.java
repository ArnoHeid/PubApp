package de.hsmainz.pubapp.poi.web;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import de.hsmainz.pubapp.poi.controller.PoiSearchService;
import de.hsmainz.pubapp.poi.controller.PoiSearchWithGooglePlaces;
import de.hsmainz.pubapp.poi.controller.PoiSearchWithOverpass;
import de.hsmainz.pubapp.poi.model.PoiBoundingBox;
import de.hsmainz.pubapp.poi.model.ResultPoi;

/**
 * This Class Handles Request sent to API Given routing Coordinates will be
 * processed and POIs according to data are generated
 * 
 * @author caro
 *
 */
@Path("poi")
public class RequestHandler {

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String get(@QueryParam("callback") String callback, @QueryParam("interest") String interest,
			@QueryParam("startLat") String startLat, @QueryParam("startLng") String startLng,
			@QueryParam("endLat") String endLat, @QueryParam("endLng") String endLng, @QueryParam("api") String api) {

		List<ResultPoi> allPois = getRelevantPois(interest, Double.parseDouble(startLat), Double.parseDouble(startLng),
				Double.parseDouble(endLat), Double.parseDouble(endLng), api);
		ResponseHandler responseHandler = new ResponseHandler();

		String response = responseHandler.getResponse(allPois);

		return addCallback(callback, response);
	}

	public String addCallback(String callback, String response) {
		if (callback.isEmpty() || callback == null) {
			return response;
		} else {
			return callback + "'('" + response + "')'";
		}

	}

	public List<ResultPoi> getRelevantPois(String interest, Double startLat, Double startLng, Double endLat,
			Double endLng, String api) {
		api = "overpass";
		String searchType = "bbox"; // should be in properties file
		PoiSearchService poiSearchService = null;
		List<PoiBoundingBox> poisToFind = new ArrayList<PoiBoundingBox>();
		List<ResultPoi> allPois = new ArrayList<>();

		// Save Given Data in PoiBoundingBoxObject
		PoiBoundingBox poiSearch = new PoiBoundingBox();
		poiSearch.setStartLat(startLat);
		poiSearch.setStartLng(startLng);
		poiSearch.setEndLat(endLat);
		poiSearch.setEndLng(endLng);

		poisToFind.add(poiSearch);

		// check if search should contact googlePlacesAPI or overpassAPI
		if ("google".equals(api)) {
			System.out.println("Use Google Places API");
			poiSearchService = new PoiSearchWithGooglePlaces();
		} else if ("overpass".equals(api)) {
			System.out.println("Use Overpass API");
			poiSearchService = new PoiSearchWithOverpass();
		}

		// check if search should be within BoundingBox or Radius of all Nodes
		if (poiSearchService != null) {
			if ("bbox".equals(searchType)) {
				System.out.println("bbox search");
				List<ResultPoi> poisForNode = poiSearchService.getPoisWithinBBox(interest, poiSearch);
				allPois.addAll(poisForNode);
			} else if ("radius".equals(searchType)) {
				System.out.println("radius search");
				for (PoiBoundingBox poi : poisToFind) {
					List<ResultPoi> poisForNode = poiSearchService.getPoisWithinRadius(interest, poi, 500);
					allPois.addAll(poisForNode);
				}
			}
		} else {
			// TODO Error: Please Select API to Use either "google" or
			// "overpass"
		}
		return allPois;
	}
}

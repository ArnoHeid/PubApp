package de.hsmainz.pubapp.poi;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import de.hsmainz.pubapp.poi.controller.PoiSearchService;
import de.hsmainz.pubapp.poi.controller.PoiSearchWithGooglePlaces;
import de.hsmainz.pubapp.poi.model.PoiToFind;
import de.hsmainz.pubapp.poi.model.ResultPoi;

@Path("poi")
public class RequestHandler {

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String get(@QueryParam("callback") String callback, @QueryParam("interest") String interest,
			@QueryParam("startLat") String startLat, @QueryParam("startLng") String startLng,
			@QueryParam("endLat") String endLat, @QueryParam("endLng") String endLng) {
		System.out.println(callback);
		System.out.println(interest);
		System.out.println(startLat);
		System.out.println(startLng);
		System.out.println(endLat);
		System.out.println(endLng);

		List<ResultPoi> allPois = getRelevantPois(interest, Double.parseDouble(startLat), Double.parseDouble(startLng),
				Double.parseDouble(endLat), Double.parseDouble(endLng));
		ResponseHandler responseHandler = new ResponseHandler();

		String response = responseHandler.getResponse(allPois);

		return response;
	}

	public String addCallback(String callback, String response) {
		if (callback.isEmpty() || callback == null)
			return response;
		else
			return callback + '(' + response + ')';
	}

	public List<ResultPoi> getRelevantPois(String interest, Double startLat, Double startLng, Double endLat,
			Double endLng) {
		List<PoiToFind> poisToFind = new ArrayList<PoiToFind>();
		List<ResultPoi> allPois = new ArrayList<>();
		PoiToFind poi1 = new PoiToFind();
		poi1.setStartLat(startLat);
		poi1.setStartLng(startLng);
		PoiToFind poi2 = new PoiToFind();
		poi2.setStartLat(endLat);
		poi2.setStartLng(endLng);
		poisToFind.add(poi1);
		poisToFind.add(poi2);

		// Switch between Google and Overpass possible through Interface
		PoiSearchService poiSearchService = new PoiSearchWithGooglePlaces();
		// PoiSearchService poiSearchService = new PoiSearchWithOverpass();

		for (PoiToFind poi : poisToFind) {
			List<ResultPoi> poisForNode = poiSearchService.getPoisWithinRadius(interest, poi, 500);
			allPois.addAll(poisForNode);
		}
		return allPois;
	}
}

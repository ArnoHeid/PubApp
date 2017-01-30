package de.hsmainz.pubapp.poi;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import de.hsmainz.pubapp.poi.controller.PoiService;
import de.hsmainz.pubapp.poi.model.PoiToFind;
import de.hsmainz.pubapp.poi.model.ResultPoi;

@Path("poi")
//Available POIs bar|cafe|biergarten|pub|car_rental|car_sharing|car_wash|dentist|dentist|pharmacy|doctors|bank|atm|fuel|ice_cream|restaurant|fast_food|brothel|stripclub|swingerclub|casino|theatre|nightclub|planetarium|gym|post_office|register_office|sauna
//social_facility|bus_station|grit_bin|clock|hunting_stand|telephone|vending_machine|waste_disposal|fire_station|school|college|kindergarten|parking|place_of_worship|bbq|bench

public class RequestHandler {

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String get(@QueryParam("callback") String callback, @QueryParam("interest") String interest, @QueryParam("startLat") String startLat,@QueryParam("startLng") String startLng, @QueryParam("endLat") String endLat, @QueryParam("endLng") String endLng) {
		System.out.println(callback);
		System.out.println(interest);
		System.out.println(startLat);
		System.out.println(startLng);
		System.out.println(endLat);
		System.out.println(endLng);

		List<ResultPoi> allPois = getRelevantPois(interest, Double.parseDouble(startLat), Double.parseDouble(startLng), Double.parseDouble(endLat), Double.parseDouble(endLng));
		ResponseHandler responseHandler = new ResponseHandler();
		String response = responseHandler.getResponseOverpass(allPois);
		
		return response;
	}
	
	public String addCallback(String callback, String response){
	       if(callback.isEmpty() || callback == null)
	           return response;
	       else
	           return callback + '(' + response + ')';
	   }
	public List<ResultPoi> getRelevantPois(String interest, Double startLat, Double startLng, Double endLat, Double endLng) {
		PoiToFind poi = new PoiToFind();
		poi.setStartLat(startLat);
		poi.setStartLng(startLng);
		poi.setEndLat(endLat);
		poi.setEndLng(endLng);

		PoiService poiService = new PoiService();

		List<ResultPoi> allPois = poiService.searchForPoiWithRadius(interest, poi, 500);
		return allPois;
	}
}

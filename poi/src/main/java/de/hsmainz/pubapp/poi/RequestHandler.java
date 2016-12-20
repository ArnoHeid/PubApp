package de.hsmainz.pubapp.poi;

import java.util.ArrayList;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import de.hsmainz.pubapp.poi.controller.PoiService;
import de.hsmainz.pubapp.poi.model.Place;
import de.hsmainz.pubapp.poi.model.Poi;

@Path("poi")

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

		ArrayList<Place> allPois = getRelevantPois(interest, Double.parseDouble(startLat), Double.parseDouble(startLng), Double.parseDouble(endLat), Double.parseDouble(endLng));
		ResponseHandler responseHandler = new ResponseHandler();
		String response = responseHandler.getResponse(allPois);
		
		return response;
	}
	
	public String addCallback(String callback, String response){
	       if(callback.isEmpty() || callback == null)
	           return response;
	       else
	           return callback + '(' + response + ')';
	   }
	public ArrayList<Place> getRelevantPois(String interest, Double startLat, Double startLng, Double endLat, Double endLng) {
		Poi poi = new Poi();
		poi.setStartLat(startLat);
		poi.setStartLng(startLng);
		poi.setEndLat(endLat);
		poi.setEndLng(endLng);

		PoiService poiService = new PoiService();

		ArrayList<Place> allPois = poiService.searchForPoiWithRadius(interest, poi, 500);
		return allPois;
	}
}

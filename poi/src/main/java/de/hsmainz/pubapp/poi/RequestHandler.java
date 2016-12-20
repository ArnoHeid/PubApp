package de.hsmainz.pubapp.poi;

import java.util.ArrayList;

import de.hsmainz.pubapp.poi.controller.PoiService;
import de.hsmainz.pubapp.poi.model.Place;
import de.hsmainz.pubapp.poi.model.Poi;

public class RequestHandler {
	

	public ArrayList<Place> getRelevantPois() {
		Poi poi = new Poi();
	    poi.setStartLat(49.995123);
	    poi.setStartLng(8.267426);
	    poi.setEndLat(49.964907);
	    poi.setEndLng(8.323301);   
	    PoiService poiService = new PoiService();
	    
	    ArrayList<Place> allPois = poiService.searchForPoiWithRadius("supermarket", poi, 500);
	    return allPois;
	}
}

package de.hsmainz.pubapp.poi.controller;

import java.io.InputStreamReader;
import java.util.List;

import de.hsmainz.pubapp.poi.model.PoiToFind;
import de.hsmainz.pubapp.poi.model.ResultPoi;

public interface PoiSearchService {
	public String buildRequest(String interest, double lat, double lng, int radius);

	public InputStreamReader postQuery(String request, InputStreamReader in);

	public List<ResultPoi> getPoisWithinRadius(String interest, PoiToFind poi, int i);

}

package de.hsmainz.pubapp.poi.controller;

import java.io.InputStreamReader;
import java.util.List;

import de.hsmainz.pubapp.poi.model.PoiBoundingBox;
import de.hsmainz.pubapp.poi.model.ResultPoi;

public interface PoiSearchService {

	String buildRequestRadius(String interest, double lat, double lng, int radius);

	InputStreamReader postQuery(String request, InputStreamReader in);

	List<ResultPoi> getPoisWithinRadius(String interest, PoiBoundingBox poi, int radius);

	List<ResultPoi> getPoisWithinBBox(String interest, PoiBoundingBox poi);

}

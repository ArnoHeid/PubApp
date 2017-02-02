package de.hsmainz.pubapp.poi.controller;

import java.io.InputStreamReader;
import java.util.List;

import de.hsmainz.pubapp.poi.model.Coordinate;
import de.hsmainz.pubapp.poi.model.ResultPoi;

public interface PoiSearchService {

	String buildRequestRadius(String interest, double lat, double lng, int radius);

	InputStreamReader postQuery(String request, InputStreamReader in);

	void setSearchType(String searchType);

	String getSearchType();

	List<ResultPoi> getPoisWithinRadius(String interest, Coordinate coord, int radius);

	List<ResultPoi> getPoisWithinBBox(String interest, Coordinate[] coords);

}

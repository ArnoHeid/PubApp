package de.hsmainz.pubapp.poi.model;

import java.util.List;


public class GooglePoiSearchResult extends GoogleResult {

	private List<GoogleResultPoi> results;

	public List<GoogleResultPoi> getList() {
		return this.results;
	}
}

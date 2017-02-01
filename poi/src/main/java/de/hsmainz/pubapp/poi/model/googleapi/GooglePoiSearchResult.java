package de.hsmainz.pubapp.poi.model.googleapi;

import java.util.List;

public class GooglePoiSearchResult extends GoogleResult {

	private List<GoogleResultPoi> results;

	public List<GoogleResultPoi> getList() {
		return this.results;
	}
}

package de.hsmainz.pubapp.poi.model.googleapi;

import java.util.List;

/**
 * Represents the list of places found returned by Google Places API
 * 
 * @author caro
 *
 */
public class GooglePoiSearchResult extends GoogleResult {

	private List<GoogleResultPoi> results;

	public List<GoogleResultPoi> getList() {
		return this.results;
	}
}

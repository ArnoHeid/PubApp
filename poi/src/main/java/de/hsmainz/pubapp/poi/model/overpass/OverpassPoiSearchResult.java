package de.hsmainz.pubapp.poi.model.overpass;

import java.util.List;

/**
 * Represents the list of places found returned by Overpass API
 * 
 * @author caro
 *
 */
public class OverpassPoiSearchResult {

	private List<OverpassResultPoi> elements;

	public List<OverpassResultPoi> getList() {
		return this.elements;
	}
}

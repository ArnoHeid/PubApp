package de.hsmainz.pubapp.poi.model;

import java.util.List;

public class PlacesResult extends Result {

	private List<Place> results;

	public List<Place> getList() {
		return this.results;
	}
}

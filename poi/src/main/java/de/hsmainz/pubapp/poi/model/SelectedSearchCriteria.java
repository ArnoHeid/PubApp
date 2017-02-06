package de.hsmainz.pubapp.poi.model;

import java.util.List;

/**
 * Class to create an Object of Client Input meaning all search criteria to
 * match POIs accordingly
 * 
 * @author caro
 *
 */
public class SelectedSearchCriteria {
	private List<Coordinate> coordinates;
	private List<String> interests;
	private String api;
	private String searchType;

	public List<Coordinate> getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(List<Coordinate> coordinates) {
		this.coordinates = coordinates;
	}

	public List<String> getInterests() {
		return interests;
	}

	public void setInterests(List<String> interests) {
		this.interests = interests;
	}

	public String getApi() {
		return api;
	}

	public void setApi(String api) {
		this.api = api;
	}

	public String getSearchType() {
		return searchType;
	}

	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}

}

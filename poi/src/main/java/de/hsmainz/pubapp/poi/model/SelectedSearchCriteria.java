package de.hsmainz.pubapp.poi.model;

import java.util.List;

public class SelectedSearchCriteria {
	private List<Coordinate> coordinates;
	private List<String> interests;

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

}

package de.hsmainz.pubapp.poi.model.googleapi;

public class GoogleResultPoi {

	private GoogleGeometry geometry;
	private String name;
	private GoogleOpeningHours openingHours;
	
	public GoogleGeometry getGeometry() {
		return geometry;
	}

	public void setGeometry(GoogleGeometry geometry) {
		this.geometry = geometry;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public GoogleOpeningHours getOpeningHours() {
		return openingHours;
	}

	public void setOpeningHours(GoogleOpeningHours openingHours) {
		this.openingHours = openingHours;
	}



}

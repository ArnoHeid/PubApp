package de.hsmainz.pubapp.poi.model;

public class GoogleResultPoi {

	private GoogleGeometry geometry;
	private String name;

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

}
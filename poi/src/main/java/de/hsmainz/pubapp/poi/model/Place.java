package de.hsmainz.pubapp.poi.model;

import java.util.List;

public class Place {

	private String id;
	private double lat;
	private double lon;
	private Double rating;
	private String name;
	private String formattedAddress;
	private String icon;
	private String reference;
	private List<String> types;

	public Place() {
	    }

	public Place(String id, double lat, double lon) {
	        this.id = id;
	        this.lat = lat;
	        this.lon = lon;
	    }

	public Place(double lat, double lon) {
	        this.lat = lat;
	        this.lon = lon;
	    }

	public double getLat() {
		return this.lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLon() {
		return this.lon;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}


	public String getFormattedAddress() {
		return formattedAddress;
	}
	
	public void setFormattedAddress(String formattedAddress) {
		this.formattedAddress = formattedAddress;
	}

	public String getIcon() {
		return icon;
	}
	
	public void setIcon(String icon) {
		this.icon = icon;
	}


	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public Double getRating() {
		return rating;
	}
	public void setRating(Double rating) {
		this.rating = rating;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public List<String> getTypes() {
		return types;
	}
	
	public void setReference(List<String> types) {
		this.types = types;
	}

}

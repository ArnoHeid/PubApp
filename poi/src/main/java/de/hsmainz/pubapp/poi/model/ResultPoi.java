package de.hsmainz.pubapp.poi.model;

/**
 * Class to create an Object of each POI found with all relevant Details
 * 
 * @author caro
 *
 */
public class ResultPoi {
	private String name;
	private String interest;
	private double lat;
	private double lon;
	private Details details;

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLon() {
		return lon;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}

	public Details getDetails() {
		return details;
	}

	public void setDetails(Details details) {
		this.details = details;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getInterest() {
		return interest;
	}

	public void setInterest(String interest) {
		this.interest = interest;
	}

}
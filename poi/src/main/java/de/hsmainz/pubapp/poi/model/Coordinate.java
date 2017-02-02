package de.hsmainz.pubapp.poi.model;

/**
 * Class for generating and saving Coordinates with their Longitude and Latitude
 * 
 * @author caro
 *
 */
public class Coordinate {
	private double lat;
	private double lng;

	public double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

}

package de.hsmainz.pubapp.poi.model.googleapi;

/**
 * Represents a places Location returned by Google Places API
 * 
 * @author caro
 *
 */
public class GoogleLocation {

	private double lat;

	private double lng;

	public double getLat() {
		return this.lat;
	}

	public void setLat(double d) {
		this.lat = d;
	}

	public double getLng() {
		return this.lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}

}

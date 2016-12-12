package de.hsmainz.pubapp.poi.model;


public class Geometry {

	private Location location;

	public Location getLocation() {
		return this.location;
	}

	@Override
	public String toString() {
		return this.getLocation().toString();
	}

}

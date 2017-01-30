package de.hsmainz.pubapp.poi.modelforgoogle;

public class Poi {
	
	private double startLat;

	private double startLng;
	
	private double endLat;

	private double endLng;
	
	private String interest;

	public double getStartLat( ) {
		return this.startLat;
	}
	
	public void setStartLat(double startLat) {
		this.startLat = startLat;
	}

	public double getStartLng( ) {
		return this.startLng;
	}
	
	public void setStartLng(double startLng) {
		this.startLng = startLng;
	}
	
	public double getEndLat( ) {
		return this.endLat;
	}
	
	public void setEndLat(double endLat) {
		this.endLat = endLat;
	}

	public double getEndLng( ) {
		return this.endLng;
	}
	
	public void setEndLng(double endLng) {
		this.endLng = endLng;
	}

	public String getInterest() {
		return interest;
	}

	public void setInterest(String interest) {
		this.interest = interest;
	}
	
}

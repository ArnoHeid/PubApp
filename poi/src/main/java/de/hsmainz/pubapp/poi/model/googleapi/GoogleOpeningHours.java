package de.hsmainz.pubapp.poi.model.googleapi;

/**
 * Represents a places information whether its open or not returned by Google
 * Places API
 * 
 * @author caro
 *
 */
public class GoogleOpeningHours {
	private Boolean openNow;

	public Boolean getOpenNow() {
		return openNow;
	}

	public void setOpenNow(Boolean openNow) {
		this.openNow = openNow;
	}

}

package de.hsmainz.pubapp.poi.model;

/**
 * Class for saving and returning relevant details to Client for each Place that
 * has been found with PoiSerach
 * 
 * @author caro
 *
 */
public class Details {

	private String openingHours;
	private String isOpen;

	public String getOpeningHours() {
		return openingHours;
	}

	public void setOpeningHours(String openingHours) {
		this.openingHours = openingHours;
	}

	public String getIsOpen() {
		return isOpen;
	}

	public void setIsOpen(String isOpen) {
		this.isOpen = isOpen;
	}

}

package de.hsmainz.pubapp.poi.model;

/**
 * Class for generating a GeoJson-Feature for saving POIs as GeoJson
 */
public class GeoJsonFeature {
	private String type;
	private GeoJsonProperties properties;
	private GeoJsonGeometry geometry;

	public GeoJsonFeature(String type, GeoJsonProperties geoJsonProperties, GeoJsonGeometry geometry) {
		this.type = type;
		this.properties = geoJsonProperties;
		this.geometry = geometry;
	}

	public String getType() {
		return type;
	}

	public GeoJsonProperties getGeoJsonProperties() {
		return properties;
	}

	public GeoJsonGeometry getGeoJsonGeometry() {
		return geometry;
	}

	public static class GeoJsonGeometry {

		private String type;
		private double[] coordinates;

		public GeoJsonGeometry(String type, double[] coordinates) {
			this.type = type;
			this.coordinates = coordinates;
		}

		public String getType() {
			return this.type;
		}

		public double[] getCoordinates() {
			return this.coordinates;
		}

	}

	public static class GeoJsonProperties {
		private String name;
		private String interest;
		private String openingHours;
		private String isOpen;

		public GeoJsonProperties(String name, String interest, String openingHours, String isOpen) {
			this.name = name;
			this.interest = interest;
			this.openingHours = openingHours; 
			this.setIsOpen(isOpen);
		}

		public String getName() {
			return this.name;
		}

		public String getInterest() {
			return interest;
		}

		public void setInterest(String interest) {
			this.interest = interest;
		}

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

}

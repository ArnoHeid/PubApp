package de.hsmainz.pubapp.poi.model;

/**
 * Class for generating a GeoJson-Feature for saving POIs as GeoJson
 */
public class GeoJsonFeature {
	private String type;
	private GeoJsonProperties properties;
	private GeoJsonGeometry geometry;

	/**
	 * Creates GeoJsonFeeature Object according to GeoJSON Syntax
	 * 
	 * @param type
	 *            defines feature type and should be "Feature" for well formed
	 *            GeoJson
	 * @param geoJsonProperties
	 *            stores all given details for POI
	 * @param geometry
	 *            stores relevant geometry information especially coordinates of
	 *            POI
	 */
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

	/**
	 * Class for saving coordinates of POI in GeoJson Format
	 * 
	 * @author caro
	 *
	 */
	public static class GeoJsonGeometry {

		private String type;
		private double[] coordinates;

		/**
		 * Creates Object with given type and coordinates of POI
		 * 
		 * @param type
		 *            should be "Point" for POI
		 * @param coordinates
		 *            Latitude and Longitude are stored in Array
		 */
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

	/**
	 * Class for saving details about POI in GeoJson Format
	 * 
	 * @author caro
	 *
	 */
	public static class GeoJsonProperties {
		private String name;
		private String interest;
		private String openingHours;
		private String isOpen;

		/**
		 * Creates Object with given Details of POI
		 * 
		 * @param name
		 *            of POI
		 * @param interest
		 *            selected category of place
		 * @param openingHours
		 *            String of available opening hours information
		 * @param isOpen
		 *            String of available information whether place is open
		 *            right now or not
		 */
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

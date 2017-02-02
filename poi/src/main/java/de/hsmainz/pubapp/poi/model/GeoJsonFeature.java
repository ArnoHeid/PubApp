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

		public GeoJsonProperties(String name) {
			this.name = name;
		}

		public String getName() {
			return this.name;
		}

	}

}

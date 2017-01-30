package de.hsmainz.pubapp.poi.modelforgoogle;

public class GeoJsonFeature {
	private String type;

	public static class GeoJsonGeometry {

		private String type;
		private double[] coordinates;
		public GeoJsonGeometry (String type, double[] coordinates) {
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


	/*
	 * ({"type":"FeatureCollection", "features": [
	 * {"type":"Feature","properties":{"name":"Mainz","country":"Deutschland"},
	 * "geometry":{"type":"Point","coordinates":[8.2710237,49.9999952]}},
	 * {"type":"Feature","properties":{"name":"Mainz","country":"Deutschland"},
	 * "geometry":{"type":"Point","coordinates":[8.25769443117735,49.
	 * 965411149999994]}},
	 * {"type":"Feature","properties":{"name":"Mainz Hbf","country":
	 * "Deutschland"},"geometry":{"type":"Point","coordinates":[8.2587297,50.
	 * 0012336]}}, ]})
	 */
}

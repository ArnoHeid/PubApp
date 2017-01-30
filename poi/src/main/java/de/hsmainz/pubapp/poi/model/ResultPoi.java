package de.hsmainz.pubapp.poi.model;

public class ResultPoi {
		
		private String type;
		
		private String id;
		
		private double lat;
		
		private double lon;
		
		private Tags tags;
		
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public double getLat() {
			return lat;
		}
		public void setLat(double lat) {
			this.lat = lat;
		}
		public double getLon() {
			return lon;
		}
		public void setLon(double lon) {
			this.lon = lon;
		}
		public Tags getTags() {
			return tags;
		}
		public void setTags(Tags tags) {
			this.tags = tags;
		}	

}
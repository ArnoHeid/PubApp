package de.hsmainz.pubapp.poi.model;

import java.util.List;

/**
 * Class for generating a GeoJson-Response of all found POIs
 * 
 * @author caro
 *
 */
public class GeoJsonFeatureCollection {
	private String type;
	private List<GeoJsonFeature> features;

	public GeoJsonFeatureCollection(String type, List<GeoJsonFeature> features) {
		this.type = type;
		this.features = features;
	}

	public List<GeoJsonFeature> getList() {
		return this.features;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}

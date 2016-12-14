package de.hsmainz.pubapp.poi.model;

import java.util.Collections;
import java.util.Set;

public class Place {
	
	private Geometry geometry;
	
	private String icon;
	
	private String id;
	
	private String name;
	
	private String placeId;
	
	private Float rating;
	
	private String reference;

	private Set<String> types = Collections.emptySet( );

	private String vicinity;

	public Geometry getGeometry( ) {
		return this.geometry;
	}

	public String getIcon( ) {
		return this.icon;
	}
	
	public String getId( ) {
		return this.id;
	}

	public String getName( ) {
		return this.name;
	}

	public String getPlaceId( ) {
		return this.placeId;
	}

	public Float getRating( ) {
		return this.rating;
	}

	public String getReference( ) {
		return this.reference;
	}

	public Set<String> getTypes( ) {
		return this.types;
	}

	public String getVicinity( ) {
		return this.vicinity;
	}
	
}
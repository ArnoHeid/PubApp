package de.hsmainz.pubapp.poi;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import de.hsmainz.pubapp.poi.model.GeoJsonFeature;
import de.hsmainz.pubapp.poi.model.GeoJsonFeature.GeoJsonGeometry;
import de.hsmainz.pubapp.poi.model.GeoJsonFeature.GeoJsonProperties;
import de.hsmainz.pubapp.poi.model.GeoJsonFeatureCollection;
import de.hsmainz.pubapp.poi.model.Place;

public class ResponseHandler {
	
	public String getResponse(ArrayList<Place> allPois) {
		
		List<GeoJsonFeature> features = new ArrayList<GeoJsonFeature>();
		for (Place place : allPois) {
			//set coordinates for GeoJsonGeometry
			double[] coordinates = new double[2];
	        coordinates[1] = place.getGeometry().getLocation().getLat();
	        coordinates[0] = place.getGeometry().getLocation().getLng();
			GeoJsonFeature currentFeature = new GeoJsonFeature("Feature", new GeoJsonProperties(place.getName()), new GeoJsonGeometry("Point", coordinates));
			features.add(currentFeature);
		}
		
		GeoJsonFeatureCollection featureCollection = new GeoJsonFeatureCollection("FeatureCollection", features);		
		Gson gson = new Gson();
		String jsonInString = gson.toJson(featureCollection);
		
		return jsonInString;
		
		/*
		 * [
		 * {"geometry":{"location":{"lat":49.9643788,"lng":8.3146131}},"name":"REWE"},
		 * {"geometry":{"location":{"lat":49.96446,"lng":8.32054}},"name":"Nahkauf"},
		 * {"geometry":{"location":{"lat":49.97296,"lng":8.315769999999999}},"name":"ADM Mainz GmbH"},
		 * {"geometry":{"location":{"lat":49.9606,"lng":8.31864}},"name":"DHL Packstation"}]
		 */
		
		/* GEO JSON ARNO
		 * ({"type":"FeatureCollection",
		 * "features":[
		 * {"type":"Feature","properties":{"name":"Mainz","country":"Deutschland"},
		 * "geometry":{"type":"Point","coordinates":[8.2710237,49.9999952]}},
		 * {"type":"Feature","properties":{"name":"Mainz","country":"Deutschland"},
		 * "geometry":{"type":"Point","coordinates":[8.25769443117735,49.965411149999994]}},
		 * {"type":"Feature","properties":{"name":"Mainz Hbf","country":"Deutschland"},
		 * "geometry":{"type":"Point","coordinates":[8.2587297,50.0012336]}},
		 * ]})
		 */
		
		/*
		 * {"type":"FeatureCollection",
		 * "features":[
		 * {"type":"Feature","properties":{"type":"name","name":"REWE"},
		 * "geometry":{"type":"Point","coordinates":[8.3146131,49.9643788]}},
		 * {"type":"Feature","properties":{"type":"name","name":"Nahkauf"},
		 * "geometry":{"type":"Point","coordinates":[8.32054,49.96446]}},
		 * {"type":"Feature","properties":{"type":"name","name":"ADM Mainz GmbH"},
		 * "geometry":{"type":"Point","coordinates":[8.315769999999999,49.97296]}},
		 * {"type":"Feature","properties":{"type":"name","name":"DHL Packstation"},
		 * "geometry":{"type":"Point","coordinates":[8.31864,49.9606]}}
		 * ]}
		 */
	}

}

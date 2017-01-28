package de.hsmainz.pubapp.geocoder.jsonparser.geoJson;

import de.hsmainz.pubapp.geocoder.jsonparser.graphhopperJson.PointJson;

/**
 * Created by Arno on 07.12.2016.
 */
public class Geometry {

    private String type;
    private double[] coordinates;

    public Geometry(PointJson point) {
        type = "Point";
        coordinates = new double[2];
        coordinates[1] = point.getLat();
        coordinates[0] = point.getLng();
    }
}

package de.hs_mainz.pubApp.jsonparser.geoJson;

import de.hs_mainz.pubApp.jsonparser.graphhopperJson.PointJson;

/**
 * Created by Arno on 07.12.2016.
 */
public class Geometry {

    private String type;
    private double[] coordinates;

    public Geometry(PointJson point) {
        type = "Point";
        coordinates = new double[2];
        coordinates[0] = point.getLat();
        coordinates[1] = point.getLng();
    }
}

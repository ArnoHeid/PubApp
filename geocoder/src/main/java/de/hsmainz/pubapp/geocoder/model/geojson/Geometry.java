package de.hsmainz.pubapp.geocoder.model.geojson;

import de.hsmainz.pubapp.geocoder.model.graphhopperjson.PointJson;

/**
 * Geometry part of the GeoJSON with the lat-long Coordinates
 *
 * @author Arno
 * @since 07.12.2016.
 */
public class Geometry {

    //****************************************
    // CONSTANTS
    //****************************************

    //****************************************
    // VARIABLES
    //****************************************

    private String type;
    private double[] coordinates;

    //****************************************
    // INIT/CONSTRUCTOR
    //****************************************

    public Geometry(PointJson point) {
        type = "Point";
        coordinates = new double[2];
        coordinates[1] = point.getLat();
        coordinates[0] = point.getLng();
    }

    public Geometry(double lat, double lon) {
        type = "Point";
        coordinates = new double[2];
        coordinates[1] = lat;
        coordinates[0] = lon;
    }

    //****************************************
    // GETTER/SETTER
    //****************************************

    public String getType() {
        return type;
    }

    public double[] getCoordinates() {
        return coordinates;
    }

    //****************************************
    // PUBLIC METHODS
    //****************************************

    //****************************************
    // PRIVATE METHODS
    //****************************************

    //****************************************
    // INNER CLASSES
    //****************************************

}

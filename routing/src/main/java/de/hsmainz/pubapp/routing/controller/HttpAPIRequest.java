package de.hsmainz.pubapp.routing.controller;

import de.hsmainz.pubapp.routing.model.geojson.GeoJsonCollection;

/**
 * @author Sarah
 * @since 20.12.2016
 */
public interface HttpAPIRequest {

    //****************************************
    // CONSTANTS
    //****************************************

    //****************************************
    // VARIABLES
    //****************************************

    //****************************************
    // INIT/CONSTRUCTOR
    //****************************************

    //****************************************
    // GETTER/SETTER
    //****************************************

    //****************************************
    // PUBLIC METHODS
    //****************************************

    /**
     *
     * @param startPoint
     * @param endPoint
     * @param locale
     * @param vehicle
     * @param pointsEncoded
     * @return
     */
    GeoJsonCollection requestRouting(String startPoint,
                                     String endPoint,
                                     String locale,
                                     String vehicle,
                                     String pointsEncoded);

    //****************************************
    // PRIVATE METHODS
    //****************************************

    //****************************************
    // INNER CLASSES
    //****************************************

}

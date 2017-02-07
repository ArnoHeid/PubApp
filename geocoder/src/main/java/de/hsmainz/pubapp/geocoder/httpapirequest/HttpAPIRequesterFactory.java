package de.hsmainz.pubapp.geocoder.httpapirequest;

/**
 * Factory to create HttpRequest for given API
 *
 * @author Arno
 * @since 06.02.2017.
 */
public class HttpAPIRequesterFactory {

    //****************************************
    // CONSTANTS
    //****************************************

    //****************************************
    // VARIABLES
    //****************************************

    //****************************************
    // INIT/CONSTRUCTOR
    //****************************************

    private HttpAPIRequesterFactory(){

    }


    //****************************************
    // GETTER/SETTER
    //****************************************

    //****************************************
    // PUBLIC METHODS
    //****************************************

    public static HttpAPIRequest createRequest(String type){

        HttpAPIRequest returnValue;

        if("graphhopper".equalsIgnoreCase(type)){
            returnValue = new HttpGraphhopperRequest();
        }else{
            returnValue = new HttpNominatimRequest();
        }

        return returnValue;
    }

    //****************************************
    // PRIVATE METHODS
    //****************************************

    //****************************************
    // INNER CLASSES
    //****************************************

}

package de.hsmainz.pubapp.geocoder;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

/**
 * Handles Properties Files
 * @author Arno
 * @since 30.01.2017.
 */
public class MyProperties extends Properties {

    //****************************************
    // CONSTANTS
    //****************************************

    private static final Logger logger = LogManager.getLogger(MyProperties.class);
    private static MyProperties instance = null;

    //****************************************
    // VARIABLES
    //****************************************

    //****************************************
    // INIT/CONSTRUCTOR
    //****************************************

    private MyProperties(){

    }

    //****************************************
    // GETTER/SETTER
    //****************************************

    //****************************************
    // PUBLIC METHODS
    //****************************************

    public static MyProperties getInstance(){
        if(instance==null){
            try {
                instance = new MyProperties();
                FileInputStream in = new FileInputStream("config.properties");
                instance.load(in);
                in.close();
            } catch (IOException e) {
                logger.catching(e);
                return null;
            }
        }
        return instance;
    }

    //****************************************
    // PRIVATE METHODS
    //****************************************

    //****************************************
    // INNER CLASSES
    //****************************************

}

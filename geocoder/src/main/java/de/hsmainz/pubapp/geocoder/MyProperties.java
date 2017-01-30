package de.hsmainz.pubapp.geocoder;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Handles Properties Files
 * @author Arno
 * @since 30.01.2017.
 */
public class MyProperties extends Properties {
    private static MyProperties instance = null;

    private MyProperties(){

    }

    public static MyProperties getInstance(){
        if(instance==null){
            try {
                instance = new MyProperties();
                FileInputStream in = new FileInputStream("blub");
                instance.loadFromXML(in);
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        return instance;
    }

}

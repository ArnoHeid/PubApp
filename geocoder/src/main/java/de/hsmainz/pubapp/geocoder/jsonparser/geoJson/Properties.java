package de.hsmainz.pubapp.geocoder.jsonparser.geoJson;

/**
 * Created by Arno on 07.12.2016.
 */
public class Properties {
    private String name;
    private String country;

    public Properties(String country, String name) {
        this.name = name;
        this.country = country;
    }
}

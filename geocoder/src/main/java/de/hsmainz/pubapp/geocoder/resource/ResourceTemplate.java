package de.hsmainz.pubapp.geocoder.resource;

/**
 * Created by Arno on 16.12.2016.
 */
public class ResourceTemplate {

   protected String jsonCallbackWraper(String callback, String json){
       if(callback.isEmpty() || callback == null)
           return json;
       else
           return callback + '(' + json + ')';
   }

}

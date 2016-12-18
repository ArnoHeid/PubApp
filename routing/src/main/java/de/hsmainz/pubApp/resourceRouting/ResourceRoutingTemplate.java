package de.hsmainz.pubApp.resourceRouting;

/**
 * Created by Sarah on 17.12.2016.
 */
public class ResourceRoutingTemplate {

    protected String jsonCallbackWrapper(String callback, String json){
        if(callback.isEmpty() || callback == null)
            return json;
        else
            return callback + '(' + json + ')';
    }

}

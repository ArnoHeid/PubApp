package de.hsmainz.pubApp.routing.resource;

/**
 * Created by Sarah on 17.12.2016.
 */
public class RoutingTemplate {

    protected String jsonCallbackWrapper(String callback, String json){
        if(callback.isEmpty() || callback == null) {
            return json;
        }
        // … else return json wrapped in callback
        return callback + '(' + json + ')';
    }

}

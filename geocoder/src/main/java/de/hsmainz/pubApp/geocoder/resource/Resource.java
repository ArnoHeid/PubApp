package de.hsmainz.pubApp.geocoder.resource;


import com.google.gson.Gson;
import de.hsmainz.pubApp.geocoder.httpApiRequest.HttpAPIRequest;
import de.hsmainz.pubApp.geocoder.httpApiRequest.HttpGraphhopperRequest;
import de.hsmainz.pubApp.geocoder.jsonparser.geoJson.GeoJsonColection;


import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;


/**
 * Created by Arno on 03.12.2016.
 */
@Path("geocoder")
public class Resource {

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String geoCoder(@QueryParam("callback") String callback,@QueryParam("queryString") String queryString, @QueryParam("locale") String locale){
        Gson gson = new Gson();

        HttpAPIRequest httpAPIRequest = new HttpGraphhopperRequest();

        GeoJsonColection geoJsonColection = httpAPIRequest.requestGeocoder(queryString,locale);

        return callback + '(' + gson.toJson(geoJsonColection) + ')';
    }


}
package de.hsmainz.pubapp.geocoder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.IOException;
import java.net.URI;

/**
 *
 *
 * @author Arno
 * @since 03.12.2016.
 */
public class GeocoderServiceMain {

    //****************************************
    // CONSTANTS
    //****************************************

    private static final String BASE_URI = "http://localhost";
    private static final String PATH = "pubapp/";
    private static final Logger logger = LogManager.getLogger(GeocoderServiceMain.class);

    //****************************************
    // VARIABLES
    //****************************************

    //****************************************
    // INIT/CONSTRUCTOR
    //****************************************

    private GeocoderServiceMain() {

    }

    //****************************************
    // GETTER/SETTER
    //****************************************

    //****************************************
    // PUBLIC METHODS
    //****************************************

    /**
     * Main server method.
     *
     * @param args load properties file
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        if(args.length > 0){
            MyProperties.setPropertiesFile(args[0]);
        }


        final HttpServer server = startServer();
        logger.trace("Geocoder started");

        System.out.println(String.format("Jersey app started with WADL available at "
                + "%sapplication.wadl\nHit enter to stop it...", buildURL()));

        System.in.read();

        logger.trace("Geocoder stoped");
        server.shutdownNow();
    }


    //****************************************
    // PRIVATE METHODS
    //****************************************

    /**
     * Starts Grizzly HTTP server
     *
     * @return Grizzly HTTP server.
     */
    private static HttpServer startServer() {

        final ResourceConfig rc = new ResourceConfig().packages("de.hsmainz.pubapp.geocoder.web");


        return GrizzlyHttpServerFactory.createHttpServer(URI.create(buildURL()), rc);
    }

    private static String buildURL(){
        String port = MyProperties.getInstance().getProperty("geo_port");
        return BASE_URI + ":" + port + "/" +PATH;
    }

    //****************************************
    // INNER CLASSES
    //****************************************

}

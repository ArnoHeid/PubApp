package de.hsmainz.pubapp.geocoder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.IOException;
import java.net.URI;

/**
 * @author Arno
 * @since 03.12.2016.
 */
public class Main {

    //****************************************
    // CONSTANTS
    //****************************************

    // URI the Grizzly HTTP server will listen on
    public static final String BASE_URI = "http://localhost:8080/pubapp/";
    private static final Logger logger = LogManager.getLogger(Main.class);
    //****************************************
    // VARIABLES
    //****************************************

    //****************************************
    // INIT/CONSTRUCTOR
    //****************************************

    private Main() {

    }

    //****************************************
    // GETTER/SETTER
    //****************************************

    //****************************************
    // PUBLIC METHODS
    //****************************************

    /**
     * Starts Grizzly HTTP server
     *
     * @return Grizzly HTTP server.
     */
    private static HttpServer startServer() {

        final ResourceConfig rc = new ResourceConfig().packages("de.hsmainz.pubapp.geocoder.resource");
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
    }

    //****************************************
    // PRIVATE METHODS
    //****************************************

    /**
     * Main server method.
     *
     * @param args load properties file
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        final HttpServer server = startServer();
        logger.trace("Geocoder started");

        System.out.println(String.format("Jersey app started with WADL available at "
                + "%sapplication.wadl\nHit enter to stop it...", BASE_URI));
        System.out.println(MyProperties.getInstance().getProperty("typ"));



        System.in.read();
        logger.trace("Geocoder stoped");
        server.shutdownNow();
    }

    //****************************************
    // INNER CLASSES
    //****************************************

}

package de.hsmainz.pubapp.geocoder;

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

    //****************************************
    // VARIABLES
    //****************************************

    //****************************************
    // INIT/CONSTRUCTOR
    //****************************************

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

        final ResourceConfig rc = new ResourceConfig().packages("de.hsmainz.pubApp.geocoder.resource");
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
    }

    //****************************************
    // PRIVATE METHODS
    //****************************************

    /**
     * Main server method.
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        final HttpServer server = startServer();
        System.out.println(String.format("Jersey app started with WADL available at "
                + "%sapplication.wadl\nHit enter to stop it...", BASE_URI));
        System.in.read();
        server.shutdownNow();
    }

    //****************************************
    // INNER CLASSES
    //****************************************

}

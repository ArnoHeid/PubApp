package de.hsmainz.pubapp.routing;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.IOException;
import java.net.URI;

/**
 * @author Sarah
 * @since 09.12.2016
 */
public class RoutingServiceMain {

    //****************************************
    // CONSTANTS
    //****************************************

    public static final String BASE_URI = "http://localhost";
    private static final String PATH = "/pubapp/";
    private static final Logger logger = LogManager.getLogger(RoutingServiceMain.class);

    //****************************************
    // VARIABLES
    //****************************************

    //****************************************
    // INIT/CONSTRUCTOR
    //****************************************

    private RoutingServiceMain() {

    }

    //****************************************
    // GETTER/SETTER
    //****************************************

    //****************************************
    // PUBLIC METHODS
    //****************************************

    /**
     * RoutingServiceMain server method.
     *
     * @param args load properties file
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        if (args.length > 0) {
            MyProperties.setPropertiesFile(args[0]);
        }

        final HttpServer server = startServer();
        logger.trace("Routing service started");

        System.out.println(String.format("Jersey app started with WADL available at "
                + "%sapplication.wadl\nHit enter to stop it...", buildURL()));

        System.in.read();

        logger.trace("Routing service stopped");
        server.shutdownNow();
    }

    //****************************************
    // PRIVATE METHODS
    //****************************************

    /**
     * Starts Grizzly HTTP server.
     *
     * @return Grizzly HTTP server.
     */
    private static HttpServer startServer() {
        final ResourceConfig rc = new ResourceConfig().packages("de.hsmainz.pubapp.routing.web");
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(buildURL()), rc);
    }

    /**
     * Get server URL.
     *
     * @return Server URL
     */
    private static String buildURL() {
        String port = MyProperties.getInstance().getProperty("routing_port");
        return BASE_URI + ":" + port + PATH;
    }

    //****************************************
    // INNER CLASSES
    //****************************************

}

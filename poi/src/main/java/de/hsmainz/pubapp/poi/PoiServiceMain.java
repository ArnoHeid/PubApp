package de.hsmainz.pubapp.poi;

import java.io.IOException;
import java.net.URI;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

/**
 * Class for starting and creating Server
 * 
 * @author caro
 *
 */
public class PoiServiceMain {

	// ****************************************
	// CONSTANTS
	// ****************************************

	public static final String BASE_URI = "http://localhost:8000/pubapp/";

	// ****************************************
	// VARIABLES
	// ****************************************

	// ****************************************
	// INIT/CONSTRUCTOR
	// ****************************************

	// ****************************************
	// GETTER/SETTER
	// ****************************************

	// ****************************************
	// PUBLIC METHODS
	// ****************************************
	/**
	 * Main method of service which will be called when service is stared
	 * 
	 * @param args
	 * @throws IOException
	 */
	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws IOException {
		final HttpServer server = startServer();
		System.out.println(String.format(
				"Jersey app started with WADL available at " + "%sapplication.wadl\nHit enter to stop it...",
				BASE_URI));
		System.in.read();
		server.stop();
	}

	/**
	 * Starting Server
	 * 
	 * @return created HttpServer
	 */
	public static HttpServer startServer() {
		final ResourceConfig rc = new ResourceConfig().packages("de.hsmainz.pubapp.poi.web");
		return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
	}
	// ****************************************
	// PRIVATE METHODS
	// ****************************************

	// *****************************************
	// INNER CLASSES
	// *****************************************

}

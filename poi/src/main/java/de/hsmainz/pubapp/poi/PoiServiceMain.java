package de.hsmainz.pubapp.poi;

import java.io.IOException;
import java.net.URI;

import org.apache.log4j.Logger;
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

	// ****************************************
	// VARIABLES
	// ****************************************
	private static Logger logger = Logger.getLogger(PoiServiceMain.class);

	// ****************************************
	// INIT/CONSTRUCTOR
	// ****************************************
	private PoiServiceMain() {

	}
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
	public static void main(String[] args) throws IOException {
		if (args.length > 0) {
			MyProperties.setPropertiesFile(args[0]);
		}
		String url = MyProperties.getInstance().getProperty("poi_url");
		if (logger.isInfoEnabled()) {
			logger.info("Starting server...");
		}
		try {
			final HttpServer server = startServer(url);
			if (logger.isInfoEnabled()) {
				logger.info("Server started... " + "reachable at:" + url);
			}
			System.in.read();
			server.shutdownNow();
		} catch (Exception e) {
			logger.error("Failed to start Server", e);
		}

	}

	/**
	 * Starting Server
	 * 
	 * @return created HttpServer
	 */
	public static HttpServer startServer(String url) {
		final ResourceConfig rc = new ResourceConfig().packages("de.hsmainz.pubapp.poi.web");
		return GrizzlyHttpServerFactory.createHttpServer(URI.create(url), rc);
	}
	// ****************************************
	// PRIVATE METHODS
	// ****************************************

	// *****************************************
	// INNER CLASSES
	// *****************************************

}

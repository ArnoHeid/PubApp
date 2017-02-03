package de.hsmainz.pubapp.poi;

import java.io.IOException;
import java.net.URI;
import java.util.ResourceBundle;

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
	private static ResourceBundle config = ResourceBundle.getBundle("config");
	private static Logger logger = Logger.getLogger(PoiServiceMain.class);
	public static final String BASE_URI = config.getString("url");

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
	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws IOException {
		if (logger.isInfoEnabled()) {
			logger.info("Starting server...");
		}
		try {
			final HttpServer server = startServer();
			if (logger.isInfoEnabled()) {
				logger.info("Server started... " + "reachable at:" + BASE_URI);
			}
			System.in.read();
			server.stop();
		} catch (Exception e) {
			logger.error("Failed to start Server", e);
		}

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

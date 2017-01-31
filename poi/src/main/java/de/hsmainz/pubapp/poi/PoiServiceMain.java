package de.hsmainz.pubapp.poi;

import java.io.IOException;
import java.net.URI;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

public class PoiServiceMain {

	public static final String BASE_URI = "http://localhost:8000/pubapp/";

	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws IOException {
		final HttpServer server = startServer();
		System.out.println(String.format(
				"Jersey app started with WADL available at " + "%sapplication.wadl\nHit enter to stop it...",
				BASE_URI));
		System.in.read();
		server.stop();
	}

	public static HttpServer startServer() {
		final ResourceConfig rc = new ResourceConfig().packages("de.hsmainz.pubapp.poi");
		return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
	}
	// Sample Get
	// localhost:8000/pubapp/poi?callback=xxx&interest=bar&startLat=48.88&startLng=8.347&endLat=49.998765&endLng=8.269193
}

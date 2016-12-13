package de.hsmainz.pubapp.poi;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import de.hsmainz.pubapp.poi.controller.PoiService;
import de.hsmainz.pubapp.poi.model.Location;
import de.hsmainz.pubapp.poi.model.Poi;
/**
 * Hello world!
 *
 */
@SuppressWarnings("restriction")
public class App 
{
	public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        server.createContext("/test", new MyHandler());
        server.setExecutor(null); // creates a default executor
        server.start();
        
        
        String sampleJson = "";
        //Call Service with given parameters
        Poi poi = new Poi();
        poi.setStartLat(49.995123);
        poi.setStartLng(8.267426);
        poi.setEndLat(49.998219);
        poi.setEndLng(8.267732);
        
        PoiService poiService = new PoiService();
        poiService.search("bar",poi, 4);
        
    }

    static class MyHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            String response = "This is the response";
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

}

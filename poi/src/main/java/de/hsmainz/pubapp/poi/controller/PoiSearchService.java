package de.hsmainz.pubapp.poi.controller;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Set;

import org.apache.log4j.Logger;

import de.hsmainz.pubapp.poi.model.Coordinate;
import de.hsmainz.pubapp.poi.model.ResultPoi;

/**
 * Abstract Class for requesting an API to get certain POIs
 * 
 * @author caro
 *
 */
public abstract class PoiSearchService {
	// ****************************************
	// CONSTANTS
	// ****************************************
	public static final Logger logger = Logger.getLogger(PoiSearchService.class);

	// ****************************************
	// VARIABLES
	// ****************************************
	private String searchType;

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
	 * Method for saving API Response for requested radius search of certain
	 * Coordinates according to the given parameters
	 * 
	 * @param interest
	 *            Interest for POI selected by client
	 * @param coord
	 *            Coordinate whose circuit should be searched
	 * @param radius
	 *            Defines search area within a certain distance of coordinate
	 * @return List of all matching POIs in area
	 */
	public abstract Set<ResultPoi> getPoisWithinRadius(String interest, Coordinate coord, int radius);

	/**
	 * Method for saving API Response for requested bounding box search of a
	 * certain area according to the given parameters
	 * 
	 * @param interest
	 *            Interest for POI selected by client
	 * @param coords
	 *            Array of the two BoundoingBox Edge Coordinates
	 * @return List of all matching POIs in area
	 */
	public abstract Set<ResultPoi> getPoisWithinBBox(String interest, Coordinate[] coords);

	/**
	 * Actual API Request
	 * 
	 * @param request
	 *            HTTP-Request String
	 * @return the API-Response stored in InputStreamReader
	 */
	InputStreamReader postQuery(String request) {
		URL url = null;
		HttpURLConnection conn;
		InputStreamReader in = null;
		try {
			url = new URL(request);
			conn = (HttpURLConnection) url.openConnection();
			in = new InputStreamReader(conn.getInputStream());

		} catch (MalformedURLException e) {
			logger.error("Error processing API URL" + e);
		} catch (IOException e) {
			logger.error("Error connecting API" + e);
		}
		return in;
	}

	/**
	 * Set search type
	 * 
	 * @param searchType
	 *            can be either "bbox" or "radius"
	 */
	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}

	/**
	 * @return given search type
	 */

	public String getSearchType() {
		return searchType;
	}

}

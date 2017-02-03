package de.hsmainz.pubapp.poi.controller;

import java.io.InputStreamReader;
import java.util.Set;

import de.hsmainz.pubapp.poi.model.Coordinate;
import de.hsmainz.pubapp.poi.model.ResultPoi;

/**
 * Interface for requesting an API to get certain POIs
 * 
 * @author caro
 *
 */
public interface PoiSearchService {

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
	Set<ResultPoi> getPoisWithinRadius(String interest, Coordinate coord, int radius);

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
	Set<ResultPoi> getPoisWithinBBox(String interest, Coordinate[] coords);

	/**
	 * Actual API Request
	 * 
	 * @param request
	 *            HTTP-Request String
	 * @return the API-Response stored in InputStreamReader
	 */
	InputStreamReader postQuery(String request);

	/**
	 * Set search type
	 * 
	 * @param searchType
	 *            can be either "bbox" or "radius"
	 */
	void setSearchType(String searchType);

	/**
	 * @return given search type
	 */

	String getSearchType();

}

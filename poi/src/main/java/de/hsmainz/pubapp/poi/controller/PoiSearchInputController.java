package de.hsmainz.pubapp.poi.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import org.apache.log4j.Logger;

import de.hsmainz.pubapp.poi.model.Coordinate;
import de.hsmainz.pubapp.poi.model.ResultPoi;
import de.hsmainz.pubapp.poi.model.SelectedSearchCriteria;

/**
 * This class controls and manages which API should be requested according to
 * client request. It is possible to call Google Places API and Overpass API.
 * 
 * @author caro
 *
 */

public class PoiSearchInputController {
	// ****************************************
	// CONSTANTS
	// ****************************************
	private static final Logger logger = Logger.getLogger(PoiSearchInputController.class);
	// ****************************************
	// VARIABLES
	// ****************************************
	private ResourceBundle config = ResourceBundle.getBundle("config");
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
	 * Process the given input by either requesting a radius search for each
	 * given interest and coordinate or a bounding box search for all interests
	 * within certain area
	 * 
	 * @param criteria
	 *            stores data for selected interests and search area in
	 *            coordinates
	 * @param poiSearchService
	 *            defines which and how API should be requested according to
	 *            searchType
	 * @return all POIs found
	 */
	public List<ResultPoi> getPoisForCriteria(SelectedSearchCriteria criteria, PoiSearchService poiSearchService) {
		List<ResultPoi> allPois = new ArrayList<>();
		Set<ResultPoi> foundPois = new HashSet<ResultPoi>();

		if (config.getString("bounding_box_search").equals(poiSearchService.getSearchType())) {
			Coordinate[] coords = new Coordinate[2];
			coords[0] = criteria.getCoordinates().get(0);
			coords[1] = criteria.getCoordinates().get(1);

			for (String currentInterest : criteria.getInterests()) {
				Set<ResultPoi> poisForBBox = poiSearchService.getPoisWithinBBox(currentInterest, coords);
				foundPois.addAll(poisForBBox);
			}

		} else {
			for (Coordinate currentCoordinate : criteria.getCoordinates()) {
				for (String currentInterest : criteria.getInterests()) {
					Set<ResultPoi> poisForNode = poiSearchService.getPoisWithinRadius(currentInterest,
							currentCoordinate, Integer.valueOf(config.getString("radius_width")));
					foundPois.addAll(poisForNode);
				}
			}

		}
		allPois.addAll(foundPois);
		if (logger.isDebugEnabled()) {
			logger.debug("Search used: " + poiSearchService.getSearchType() + "Number of POIs found : " + "Map: "
					+ foundPois.size() + "List: " + allPois.size());
		}
		return allPois;

	}
	// ****************************************
	// PRIVATE METHODS
	// ****************************************

	// *****************************************
	// INNER CLASSES
	// *****************************************

}
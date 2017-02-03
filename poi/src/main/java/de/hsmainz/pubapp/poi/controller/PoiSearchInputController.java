package de.hsmainz.pubapp.poi.controller;

import java.util.ArrayList;
import java.util.List;

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

		if ("bbox".equals(poiSearchService.getSearchType())) {
			Coordinate[] coords = new Coordinate[2];
			coords[0] = criteria.getCoordinates().get(0);
			coords[1] = criteria.getCoordinates().get(1);

			for (String currentInterest : criteria.getInterests()) {
				List<ResultPoi> poisForBBox = poiSearchService.getPoisWithinBBox(currentInterest, coords);
				allPois.addAll(poisForBBox);
			}

		} else {
			for (Coordinate currentCoordinate : criteria.getCoordinates()) {
				for (String currentInterest : criteria.getInterests()) {
					List<ResultPoi> poisForNode = poiSearchService.getPoisWithinRadius(currentInterest,
							currentCoordinate, 1000);
					allPois.addAll(poisForNode);
				}
			}

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
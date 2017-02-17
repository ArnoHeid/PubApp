package de.hsmainz.pubapp.poi.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
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
	private final ResourceBundle config = ResourceBundle.getBundle("config");
	private final ResourceBundle lables = ResourceBundle.getBundle("lables", Locale.getDefault());
	private final String bboxString = config.getString("bounding_box_search");
	private final String radiusString = config.getString("radius_search");
	private final String googleString = config.getString("google_places_api");
	private final String overpassString = config.getString("overpass_api");

	String errorMessage = "";
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
	public List<ResultPoi> getPoisForCriteria(SelectedSearchCriteria criteria, String searchType, String api) {
		PoiSearchService poiSearchService = getPoiSearchService(criteria, searchType, api);
		List<ResultPoi> allPois = new ArrayList<>();
		Set<ResultPoi> foundPois = new HashSet<>();

		if (bboxString.equals(poiSearchService.getSearchType())) {
			Coordinate[] coords = new Coordinate[2];
			coords[0] = criteria.getCoordinates().get(0);
			coords[1] = criteria.getCoordinates().get(criteria.getCoordinates().size() - 1);

			for (String currentInterest : criteria.getInterests()) {
				Set<ResultPoi> poisForBBox = poiSearchService.getPoisWithinBBox(currentInterest, coords);
				foundPois.addAll(poisForBBox);
			}

		} else if (radiusString.equals(poiSearchService.getSearchType())) {
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

	public String validateInput(SelectedSearchCriteria criteria, String api, String searchType) {
		if (criteria != null) {
			if (bboxString.equalsIgnoreCase(searchType) && criteria.getCoordinates().size() < 2) {
				errorMessage = lables.getString("error_bbox_amout_coords");
			}

			if (criteria.getInterests() == null || criteria.getInterests().isEmpty()) {
				errorMessage = lables.getString("error_no_interest");
			}

		} else {
			errorMessage = lables.getString("error_criteria");
		}

		return errorMessage;
	}

	// ****************************************
	// PRIVATE METHODS
	// ****************************************

	private PoiSearchService getPoiSearchService(SelectedSearchCriteria criteria, String searchType, String api) {
		PoiSearchService poiSearchService = null;
		if (bboxString.equalsIgnoreCase(searchType)) {
			poiSearchService = new PoiSearchWithOverpass();
			poiSearchService.setSearchType(searchType);
		} else if (radiusString.equalsIgnoreCase(searchType)) {
			if (googleString.equals(api)) {
				poiSearchService = new PoiSearchWithGooglePlaces();
			} else if (overpassString.equals(api)) {
				poiSearchService = new PoiSearchWithOverpass();
			} else {
				if ("google".equalsIgnoreCase(config.getString("standard_api"))) {
					poiSearchService = new PoiSearchWithGooglePlaces();
				} else if ("overpass".equalsIgnoreCase(config.getString("standard_api"))) {
					poiSearchService = new PoiSearchWithOverpass();
				}
			}
			poiSearchService.setSearchType(radiusString);
		} else if (googleString.equals(api)) {
			poiSearchService = new PoiSearchWithGooglePlaces();
			poiSearchService.setSearchType(radiusString);
		} else if (overpassString.equalsIgnoreCase(api)) {
			poiSearchService = new PoiSearchWithOverpass();
			poiSearchService.setSearchType(config.getString("standard_search_type"));

		} else {
			if ("google".equalsIgnoreCase(config.getString("standard_api"))) {
				poiSearchService = new PoiSearchWithGooglePlaces();
				poiSearchService.setSearchType(radiusString);
			} else if ("overpass".equalsIgnoreCase(config.getString("standard_api"))) {
				poiSearchService = new PoiSearchWithOverpass();
				poiSearchService.setSearchType(config.getString("standard_search_type"));
			}

			if (poiSearchService == null) {
				logger.error("No valid standard data for search available");
			}

		}
		return poiSearchService;
	}

	// *****************************************
	// INNER CLASSES
	// *****************************************

}
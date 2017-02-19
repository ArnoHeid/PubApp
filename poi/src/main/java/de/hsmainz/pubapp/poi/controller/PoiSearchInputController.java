package de.hsmainz.pubapp.poi.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;

import org.apache.log4j.Logger;

import de.hsmainz.pubapp.poi.MyProperties;
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
	private final ResourceBundle lables = ResourceBundle.getBundle("lables", Locale.getDefault());
	private final String bboxString = MyProperties.getInstance().getProperty("poi_bounding_box_search");
	private final String radiusString = MyProperties.getInstance().getProperty("poi_radius_search");
	private final String googleString = MyProperties.getInstance().getProperty("poi_google_places_api");
	private final String overpassString = MyProperties.getInstance().getProperty("poi_overpass_api");
	private final String standardSearchType = MyProperties.getInstance().getProperty("poi_standard_search_type");
	private final String standardApi = MyProperties.getInstance().getProperty("poi_standard_api");
	// private String radius =
	// MyProperties.getInstance().getProperty("poi_radius_width");
	private static final Logger logger = Logger.getLogger(PoiSearchInputController.class);
	// ****************************************
	// VARIABLES
	// ****************************************
	public String errorMessage = "";
	private String radius = MyProperties.getInstance().getProperty("poi_radius_width");

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
	 * @param searchType
	 *            given type of search either radius or bounding box search
	 * @param api
	 *            given api to use, either overpassAPI or googlePlacesAPI
	 * @return all POIs found
	 */
	public List<ResultPoi> getPoisForCriteria(SelectedSearchCriteria criteria, String searchType, String api) {
		PoiSearchService poiSearchService = getPoiSearchService(searchType, api);
		List<ResultPoi> allPois = new ArrayList<>();
		Set<ResultPoi> foundPois = new HashSet<>();

		if (bboxString.equals(poiSearchService.getSearchType())) {
			Coordinate[] coords = new Coordinate[2];
			coords[0] = criteria.getCoordinates().get(0);
			coords[1] = criteria.getCoordinates().get(1);

			for (String currentInterest : criteria.getInterests()) {
				Set<ResultPoi> poisForBBox = poiSearchService.getPoisWithinBBox(currentInterest, coords);
				foundPois.addAll(poisForBBox);
			}

		} else if (radiusString.equals(poiSearchService.getSearchType())) {
			int elementsToConsider = 20;
			if (criteria.getCoordinates().size() >= 200 && criteria.getCoordinates().size() <= 1000) {
				elementsToConsider = 100;
			} else if (criteria.getCoordinates().size() >= 1000 && criteria.getCoordinates().size() <= 5000) {
				elementsToConsider = 200;
			} else if (criteria.getCoordinates().size() > 5000) {
				elementsToConsider = 500;
				radius = "10000";
			}

			for (int i = 0; i <= criteria.getCoordinates().size(); i += elementsToConsider) {
				Coordinate currentCoordinate = criteria.getCoordinates().get(i);
				for (String currentInterest : criteria.getInterests()) {
					Set<ResultPoi> poisForNode = poiSearchService.getPoisWithinRadius(currentInterest,
							currentCoordinate, Integer.valueOf(radius));
					foundPois.addAll(poisForNode);
				}
			}

			// Always consider area at the end of the route and check if picked
			// coordinate is not null
			if (criteria.getCoordinates().size() > 20) {
				for (String currentInterest : criteria.getInterests()) {
					if (criteria.getCoordinates().get(criteria.getCoordinates().size() - 10) != null) {
						Set<ResultPoi> poisForNode = poiSearchService.getPoisWithinRadius(currentInterest,
								criteria.getCoordinates().get(criteria.getCoordinates().size() - 10),
								Integer.valueOf(radius));
						foundPois.addAll(poisForNode);
					}
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

	/**
	 * Validates if client input can be processed and generates error messages
	 * if not
	 * 
	 * @param criteria
	 *            given search criteria as java object
	 * @param searchType
	 *            given type of search, should be either radius or bounding box
	 *            search
	 * @return error message according to problem or empty string if data is
	 *         valid
	 */

	public String validateInput(SelectedSearchCriteria criteria, String searchType) {
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
	/**
	 * Determines which API should be used considering also the serach type
	 * 
	 * @param searchType
	 * @param api
	 * @return certain PoiSearchService according to given data
	 */
	private PoiSearchService getPoiSearchService(String searchType, String api) {
		PoiSearchService poiSearchService = null;
		if (bboxString.equals(searchType)) {
			poiSearchService = new PoiSearchWithOverpass();
			poiSearchService.setSearchType(searchType);
		} else if (googleString.equals(api)) {
			poiSearchService = new PoiSearchWithGooglePlaces();
			poiSearchService.setSearchType(radiusString);
		} else if (overpassString.equalsIgnoreCase(api)) {
			poiSearchService = new PoiSearchWithOverpass();
			if (searchType.equals(bboxString) || searchType.equals(radiusString)) {
				poiSearchService.setSearchType(searchType);
			} else {
				poiSearchService.setSearchType(standardSearchType);
			}
		} else {
			if (googleString.equalsIgnoreCase(standardApi)) {
				poiSearchService = new PoiSearchWithGooglePlaces();
				poiSearchService.setSearchType(radiusString);
			} else if (overpassString.equalsIgnoreCase(standardApi)) {
				poiSearchService = new PoiSearchWithOverpass();
				poiSearchService.setSearchType(standardSearchType);
			} else {
				logger.error("No valid standard data for search available");
			}

		}
		return poiSearchService;
	}

	// *****************************************
	// INNER CLASSES
	// *****************************************

}
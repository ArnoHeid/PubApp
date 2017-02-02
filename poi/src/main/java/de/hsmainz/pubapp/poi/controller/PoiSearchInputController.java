package de.hsmainz.pubapp.poi.controller;

import java.util.ArrayList;
import java.util.List;

import de.hsmainz.pubapp.poi.model.Coordinate;
import de.hsmainz.pubapp.poi.model.ResultPoi;
import de.hsmainz.pubapp.poi.model.SelectedSearchCriteria;

public class PoiSearchInputController {

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
							currentCoordinate, 100);
					allPois.addAll(poisForNode);
				}
			}

		}
		return allPois;

	}
}
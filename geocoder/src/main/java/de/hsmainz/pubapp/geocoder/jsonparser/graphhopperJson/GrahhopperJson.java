package de.hsmainz.pubapp.geocoder.jsonparser.graphhopperJson;

import java.util.List;

/**
 * Created by Arno on 03.12.2016.
 */
public class GrahhopperJson {
      private String locale;

      public List<HitsJson> getHits() {
            return hits;
      }

      private List<HitsJson> hits;

}

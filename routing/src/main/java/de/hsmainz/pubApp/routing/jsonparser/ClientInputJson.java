// TODO remove, because never used!?
package de.hsmainz.pubApp.routing.jsonparser;

/**
 * Created by Sarah.
 */
public class ClientInputJson {

    private String startPoint;

    public String getStartPoint() {
        return startPoint;
    }

    private String endPoint;

    public String getEndPoint() {
        return endPoint;
    }

    private String vehicle;

    public String getVehicle() {
        return vehicle;
    }

    private String locale;

    public String getLocale() {
        return locale;
    }

    private String pointsEncoded; // TODO decide true or false => decode on the client or bigger/slower network request

    public String getPointsEncoded() {
        return pointsEncoded;
    }
}
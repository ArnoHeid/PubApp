package de.hsmainz.pubapp.poi.model;

/**
 * Error with Error message
 *
 * @author caro
 */
public class ReturnError {

	private String type;
	private String errorMessage;

	/**
	 * Generates ErrorMessage containing error type and message
	 * 
	 * @param type
	 *            should be "Error" and defines type of Message
	 * @param errorMessage
	 *            contains relevant error details for Client and User
	 */
	public ReturnError(String type, String errorMessage) {
		this.type = type;
		this.errorMessage = errorMessage;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}

package it.cfCalculator;
/**
 * <p>
 * Eccezione lanciata dal servizio di calcolo codice fiscale.
 * </p>
 * @author csciandrone
 *
 */
public class CfValidationException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -200636314887696659L;

	public CfValidationException() {
		// TODO Auto-generated constructor stub
	}

	public CfValidationException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public CfValidationException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public CfValidationException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public CfValidationException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

}

package ar.edu.itba.it.dev.common.jpa.collections;

/**
 * Raised when a comparator couln't be converted to a criteria
 */
public class UnknownComparatorException extends RuntimeException {

	public UnknownComparatorException() {
		super();
	}

	public UnknownComparatorException(String message, Throwable cause) {
		super(message, cause);
	}

	public UnknownComparatorException(String message) {
		super(message);
	}

	public UnknownComparatorException(Throwable cause) {
		super(cause);
	}

}

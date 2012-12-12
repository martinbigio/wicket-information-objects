package ar.edu.itba.it.dev.common.jpa.collections;

/**
 * Thrown to indicate that a find operation would match more than one object
 */
public class TooManyResultsException extends RuntimeException {
	public TooManyResultsException() {
		super();
	}
}

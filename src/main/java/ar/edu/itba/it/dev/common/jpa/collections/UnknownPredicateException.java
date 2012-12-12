package ar.edu.itba.it.dev.common.jpa.collections;

/**
 * Thrown to indicate that a predicate couldn't be recognized, and hence
 * couldn't be transalated into a criteria or query
 */
public class UnknownPredicateException extends RuntimeException {

	public UnknownPredicateException() {
		super();
	}

	public UnknownPredicateException(String message, Throwable cause) {
		super(message, cause);
	}

	public UnknownPredicateException(String message) {
		super(message);
	}

	public UnknownPredicateException(Throwable cause) {
		super(cause);
	}
}

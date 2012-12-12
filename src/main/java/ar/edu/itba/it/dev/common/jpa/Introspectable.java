package ar.edu.itba.it.dev.common.jpa;

/**
 * Interface that allows to extract fields from classes in a programmatic way (without the need of reflection).
 */
public interface Introspectable {
	public <T> T getValue(String key);
}

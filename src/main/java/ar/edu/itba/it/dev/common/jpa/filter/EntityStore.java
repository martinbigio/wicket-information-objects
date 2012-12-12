package ar.edu.itba.it.dev.common.jpa.filter;


/**
 * Simple entity resolver that delegates finding entities to the underlying storage
 */
public interface EntityStore {
	/**
	 * Fetch an entity
	 * @param <T> The type of the entity
	 * @param type The type of the entity
	 * @param id The identifier of the entity
	 * @return The entity requested
	 */
	public <T> T fetch(Class<T> type, Integer id);
	
	/**
	 * Returns the id associated with an object
	 * @param object The object whose id iis to be extracted
	 * @return The extracted id
	 * @throws IllegalStateException if the object is not persistent
	 */
	public Integer getId(Object o);
}

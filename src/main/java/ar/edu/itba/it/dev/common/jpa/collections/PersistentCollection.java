package ar.edu.itba.it.dev.common.jpa.collections;

import java.util.List;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;

/**
 * A collection of objects that are somehow backed by a persistent storage.
 * Unlike standard collections, these collections provide means to
 * find a specific item, or a list of items, based on Predicates.
 * 
 * <p>It is expected that for each object type there is a predicate builder file
 * that helps clients build the predicates needed to return the instance </p>
 * 
 * <p>While the interface allows any custom predicate to be used, it is strongly 
 * advised to stick with the predefined ones, as some implementations may translate 
 * those into other dialect (i.e. SQL), and will fail upon unknown ones.</p>
 */
public interface PersistentCollection<T> {
	/**
	 * Adds a new object to the collection.
	 */
	public void add(T obj);
	
	/**
	 * Removes an object from the collection.
	 * If the object is not part of the collection, this method will silently do nothing.
	 */
	public void remove(T obj);
	
	/**
	 * Finds an element satisfying the given predicate. This method expects to return only one element.
	 * To find several elements user <code>list(...)</code> 
	 * @return The optional result or absent if no no result was found
	 * @throws TooManyResultsException if there is more than one element matching the given predicate
	 */
	public Optional<T> find(Predicate<T> predicate);
	
	/**
	 * Determines if there is at least one element in the collection that satisfies a given predicate
	 * @return true if at least one element satisfies the predicate
	 */
	public boolean exists(Predicate<T> predicate);
	
	/**
	 * Returns the number of elements in the collection satisfying a predicate
	 */
	public int count(Predicate<T> predicate);

	/**
	 * Returns a list of elements that satisfy the given filter
	 */
	public List<T> list(Filter<T> filter);
	
}

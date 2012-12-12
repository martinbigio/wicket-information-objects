package ar.edu.itba.it.dev.common.jpa.collections;

import org.hibernate.Criteria;

import com.google.common.base.Predicate;

/**
 * Predicate that also provides information on how to map itself to a criteria for the same type
 */
public interface HibernatePredicate<T> extends Predicate<T> {
	/**
	 * Adds the current predicate to an existing criteria
	 * @param criteria
	 */
	public void composeInto(Criteria criteria);
}

package ar.edu.itba.it.dev.common.jpa.collections;

import java.util.Comparator;

import org.hibernate.Criteria;

/**
 * Comparator that also knows how to map itself to a criteria
 */
public interface HibernateComparator<T> extends Comparator<T> {
	/**
	 * Adds the current comparator to an existing criteria
	 * @param criteria
	 */
	public void composeInto(Criteria criteria);
}

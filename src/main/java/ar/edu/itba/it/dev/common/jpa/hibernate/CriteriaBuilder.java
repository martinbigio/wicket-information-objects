/*
 * Copyright (c) 2011 IT - ITBA -- All rights reserved
 */ 
package ar.edu.itba.it.dev.common.jpa.hibernate;

import org.hibernate.criterion.DetachedCriteria;

/**
 * Criteria builder that is capable of constructing a criteria form a given 
 * filter class.
 * @param <T> the class to use as a base for filtering
 */
public interface CriteriaBuilder<T> {
	
	/**
	 * Return a detached criteria that can be used to query
	 * @param filter The information used to build the criteria
	 * @return A criteria than can be used to query or count results
	 */
	public DetachedCriteria build(T filter);
}

/*
 * Copyright (c) 2009 IT - ITBA -- All rights reserved
 */
package ar.edu.itba.it.dev.common.jpa.filter;

import java.io.Serializable;

/**
 * Base filter class that holds possible sort criterias, and paging (limit, count) information
 * <p>
 * This class is intended to be used as a base class for filter implementations, so that inherited
 * classes take care of the actual filtering parameters. 
 * </p>
 */
public abstract class AbstractFilter implements Serializable {
	
	private int first = 0;
	private int count = -1;
	
	private String sortCriteria;
	private boolean sortAscending;

	public void setLimits(int first, int count) {
		this.first = first;
		this.count = count;
	}
	
	public void setSort(String criteria, boolean ascending) {
		this.sortCriteria = criteria;
		this.sortAscending = ascending;
	}
	
	public int getFirst() {
		return first;
	}
	
	public int getCount() {
		return count;
	}
	
	public String getSortCriteria() {
		return sortCriteria;
	}
	
	public boolean isSortAscending() {
		return sortAscending;
	}
}

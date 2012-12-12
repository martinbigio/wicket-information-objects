/*
 * Copyright (c) 2009 IT - ITBA -- All rights reserved
 */
package ar.edu.itba.it.dev.common.web.datatable;

import java.util.Iterator;
import java.util.List;

import org.apache.wicket.extensions.markup.html.repeater.data.sort.ISortState;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.ISortStateLocator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.ISortableDataProvider;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.IFilterStateLocator;
import org.apache.wicket.extensions.markup.html.repeater.util.SingleSortState;
import org.apache.wicket.extensions.markup.html.repeater.util.SortParam;
import org.apache.wicket.model.IModel;

import com.google.common.collect.Iterators;

public abstract class EnhancedDataProvider<T, F> implements ISortableDataProvider<T> {
	private IFilterStateLocator<F> filteredStateLocator = new IFilterStateLocator<F>() {

		private F filterState;

		@Override
		public F getFilterState() {
			if (this.filterState == null) {
				this.filterState = getDefaultFilterState();
			}
			return this.filterState;
		}

		@Override
		public void setFilterState(F state) {
			this.filterState = state;
			
		}
	};
	
	private ISortStateLocator sortStateLocator = new ISortStateLocator() {
		
		private ISortState sortState;

		@Override
		public ISortState getSortState() {
			if (this.sortState == null) {
				SingleSortState def = new SingleSortState();
				def.setSort(getDefaultSortParam());
				this.sortState = def;
			}
			return sortState;
		}
	};
	
	@Override
	public ISortState getSortState() {
		return sortStateLocator.getSortState();
	}


	@Override
	public final Iterator<T> iterator(int first, int count) {
		if (count > 0) {
			return getList(this.filteredStateLocator.getFilterState(),
		               ((SingleSortState)this.sortStateLocator.getSortState()).getSort(),
		               first, count).iterator();
		}
		return Iterators.emptyIterator();
	}

	@Override
	public final IModel<T> model(T object) {
		return itemModel(object);
	}

	protected abstract IModel<T> itemModel(T object);

	@Override
	public final int size() {
		return size(filteredStateLocator.getFilterState());
	}

	@Override
	public final void detach() {
		// no transient state
	}
	
	public IFilterStateLocator<F> getFilteredStateLocator() {
		return filteredStateLocator;
	}


	public ISortStateLocator getSortStateLocator() {
		return sortStateLocator;
	}
	

	/**
	 * Returns the default sort field and order
	 * @return
	 */
	protected abstract SortParam getDefaultSortParam();

	/**
	 * Returns the default filter state
	 * @return
	 */
	protected abstract F getDefaultFilterState();
	
	
	/**
	 * Returns the amount of items that match the given filter
	 * @param filter Filter instance
	 * @return The amount of items matching the filter
	 */
	protected abstract int size(F filter);
	
	/**
	 * Returns a list of items. the list is made by removing from the universe all
	 * the items not matching the filter, sorting them by the sort criteria, skipping
	 * the first elements, and trimming the list at a fixed number of elements 
	 * 
	 * @param filter the filter used to match items
	 * @param sort The sort criteria 
	 * @param first The number of items to skip
	 * @param count The maximum number of items to return
	 * @return A list with the resulting items
	 */
	protected abstract List<T> getList(F filter, SortParam sort, int first, int count);
	
	/**
	 * changes the default implementation of the filter locator with a new one.
	 * this method is intended to replace the default implementation in cases were it may not be possible
	 * to use the default behaviour of serializing the entire filter object into the page map.
	 * For these cases, it is expected that the locater itself if serializable, and has enough information to rebuild 
	 * the filter 
	 * @param locator new locator implementation to use
	 */
	protected void setfilterStateLocator(IFilterStateLocator<F> locator) {
		this.filteredStateLocator = locator;
	}
	
	/**
	 * Resets the filter back to the default
	 */
	public void resetFilter() {
		getFilteredStateLocator().setFilterState(getDefaultFilterState());
	}
}

/*
 * Copyright (c) 2009 IT - ITBA -- All rights reserved
 */
package ar.edu.itba.it.dev.common.web.datatable;

import java.util.List;

import org.apache.wicket.extensions.ajax.markup.html.repeater.data.table.AjaxFallbackHeadersToolbar;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.NoRecordsToolbar;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.FilterForm;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.FilterToolbar;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.IFilterStateLocator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.IFilteredColumn;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.OddEvenItem;
import org.apache.wicket.model.IModel;

import ar.edu.itba.it.dev.common.web.actions.IAction;

import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

/**
 * Data table that implements paging, sorting and column filtering.
 * <p>
 * The default implementation of a big table will take care of creating a form
 * that handles filter changing posts, and will add tool bars to navigate, sort
 * and filter results
 */
public class EnhancedDataTable<T, F> extends Panel {

	private static final int DEFAULT_PAGE_SIZE = 20;

	private EnhancedDataProvider<T, F> dataProvider;

	private FilterForm<F> form;
	private FullFeaturedDataTable dataTable;

	private int pageSize;
	private List<IColumn<T>> columns = Lists.newArrayList();
	private ActionColumn<T> actionColumn;
	private boolean downloadable;
	
	/**
	 * Enhanced data table that allows sorting, filtering and page navigation,
	 * all at once, displays a message when there are no items to display, and
	 * adds class odd or even to rows, alternating these values
	 */
	private class FullFeaturedDataTable extends DataTable<T> {
		
		@SuppressWarnings("unchecked")
		public FullFeaturedDataTable(String id, FilterForm<F> form, final List<IColumn<T>> columns, final EnhancedDataProvider<T, F> dataProvider, int rowsPerPage,
				boolean downloadable) {
			super(id, columns, dataProvider, rowsPerPage);

			setOutputMarkupId(true);
			setVersioned(false);
			
			addTopToolbar(new EnhancedNavigationToolbar<T>(this));
			addTopToolbar(new AjaxFallbackHeadersToolbar(this, dataProvider.getSortStateLocator()));
			
			// Add the filtering toolbar only if there is at least a column that can be filtered
			if (hasFilterColumns()) {
				addTopToolbar(new FilterToolbar(this, (FilterForm<T>)form, (IFilterStateLocator<T>)dataProvider.getFilteredStateLocator()));
			}

			addBottomToolbar(new NoRecordsToolbar(this));
		}

		@Override
		protected Item<T> newRowItem(String id, int index, IModel<T> model) {
			return new OddEvenItem<T>(id, index, model);
		}
		
	}
	
	public EnhancedDataTable(String id, EnhancedDataProvider<T, F> dataProvider) {
		this(id, dataProvider, DEFAULT_PAGE_SIZE);
	}

	public EnhancedDataTable(String id, EnhancedDataProvider<T, F> dataProvider, int pageSize) {
		super(id);
		Preconditions.checkNotNull(dataProvider, "No data provider was passed!");
		Preconditions.checkArgument(pageSize > 0, "Page size must be positive");

		this.dataProvider = dataProvider;
		this.pageSize = pageSize;
		this.actionColumn = new ActionColumn<T>();
		this.downloadable = true;
		setOutputMarkupId(true);

		this.form = new FilterForm<F>("filter-form", dataProvider.getFilteredStateLocator());
		add(form);
	}

	public EnhancedDataTable<T, F> addColumn(IColumn<T> column) {
		Preconditions.checkNotNull(column, "Can't add columns a null builder!");
		Preconditions.checkNotNull(this.columns, "Can't add columns at this time!");
		this.columns.add(column);
		return this;
	}

	public EnhancedDataTable<T, F> addAction(IAction<T> action) {
		Preconditions.checkNotNull(action, "Can't add actions at this moment!");
		this.actionColumn.addAction(action);
		return this;
	}
	
	public EnhancedDataTable<T, F> downloadable(boolean downloadable) {
		this.downloadable = downloadable;
		return this;
	}

	@Override
	protected void onBeforeRender() {
		// At this time, create the data table based on the defined columns
		if (this.dataTable == null) {
			Preconditions.checkArgument(!this.columns.isEmpty(), "No columns have been defined!");

			add(new ActionExplanationPanel<T>("explain", actionColumn.actions));
			if(canExecuteAnyAction() || hasFilterColumns()) {
				this.columns.add(actionColumn);
			}
			this.dataTable = new FullFeaturedDataTable("results", this.form, this.columns, this.dataProvider, this.pageSize, this.downloadable);
			form.add(this.dataTable);
			
			this.columns = null;
			this.actionColumn = null;
		}

		super.onBeforeRender();
	}
	
	private boolean canExecuteAnyAction() {
		return Iterables.any(actionColumn.actions, new Predicate<IAction<T>>() {

			@Override
			public boolean apply(IAction<T> input) {
				return input.canExecute();
			}
		});
	}
	
	private boolean hasFilterColumns() {
		for(IColumn<T> column : columns) {
			if(column instanceof IFilteredColumn<?> && !(column instanceof ActionColumn<?>)) {
				return true;
			}
		}
		return false;
	}
	
}

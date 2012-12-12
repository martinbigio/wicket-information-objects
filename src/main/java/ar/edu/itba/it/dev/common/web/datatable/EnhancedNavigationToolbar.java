/*
 * Copyright (c) 2010 IT - ITBA -- All rights reserved
 */ 
package ar.edu.itba.it.dev.common.web.datatable;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractToolbar;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.NavigatorLabel;
import org.apache.wicket.markup.html.WebComponent;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.model.Model;

public class EnhancedNavigationToolbar<T> extends AbstractToolbar {

	public EnhancedNavigationToolbar(final DataTable<T> table) {
		super(table);
	}
	
	@Override
	protected void onInitialize() {
		super.onInitialize();
		WebMarkupContainer span = new WebMarkupContainer("span");
		add(span.add(AttributeModifier.replace("colspan", new Model<String>(String.valueOf(getTable().getColumns().size())))));

		span.add(newPagingNavigator("navigator", getTable()));
		span.add(newNavigatorLabel("navigatorLabel", getTable()));
	}
	
	/**
	 * Factory method used to create the paging navigator that will be used by the datatable
	 * 
	 * @param navigatorId
	 *            component id the navigator should be created with
	 * @param table
	 *            dataview used by datatable
	 * @return paging navigator that will be used to navigate the data table
	 */
	protected PagingNavigator newPagingNavigator(String navigatorId, final DataTable<?> table) {
		return new PagingNavigator(navigatorId, table) {
			@Override
			protected void onConfigure() {
				super.onConfigure();
				this.setVisible(table.getRowCount() > table.getItemsPerPage());
			}
		};
	}

	/**
	 * Factory method used to create the navigator label that will be used by the datatable
	 * 
	 * @param navigatorId
	 *            component id navigator label should be created with
	 * @param table
	 *            dataview used by datatable
	 * @return navigator label that will be used to navigate the data table
	 * 
	 */
	protected WebComponent newNavigatorLabel(String navigatorId, final DataTable<?> table) {
		return new NavigatorLabel(navigatorId, table) {
			@Override
			protected void onConfigure() {
				super.onConfigure();
				this.setVisible(table.getRowCount() > table.getItemsPerPage());
			}
		};
	}


	/*
	 * (non-Javadoc)
	 * @see org.apache.wicket.Component#onBeforeRender()
	 */
	@Override
	protected void onBeforeRender() {
		super.onBeforeRender();
	}
	
	@Override
	protected void onConfigure() {
		this.setVisible(this.getTable().getRowCount() > 0);
	}
}

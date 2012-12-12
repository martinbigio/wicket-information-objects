/*
 * Copyright (c) 2009 IT - ITBA -- All rights reserved
 */
package ar.edu.itba.it.dev.common.web.datatable.columns;

import org.apache.wicket.Component;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.FilterForm;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;


/**
 * {@code FilteredPropertyColumn} that filters using a {@code CheckBoxFilter} and displays for each row a disabled {@code CheckBox}
 * indicating whether the boolean model is true or false.
 */
class CheckBoxFilteredColumn<T> extends StyledFilterColumn<T> {

	public CheckBoxFilteredColumn(ColumnBuilder<T> builder) {
		super(builder);
	}
	
	@Override
	public Component getFilter(String componentId, FilterForm<?> form) {
		CheckBoxFilter filter = new CheckBoxFilter(componentId, getFilterModel(form), form);
		return filter;
	}

	protected IModel<Boolean> getFilterModel(FilterForm<?> form) {
		return new PropertyModel<Boolean>(form.getModel(), getFilterProperty());
	}
	
	@Override
	public void populateItem(Item<ICellPopulator<T>> item, String componentId, IModel<T> rowModel) {
		item.add(new CheckBoxPanel(componentId, new PropertyModel<Boolean>(rowModel, getPropertyExpression())).setEnabled(false));
	}
}

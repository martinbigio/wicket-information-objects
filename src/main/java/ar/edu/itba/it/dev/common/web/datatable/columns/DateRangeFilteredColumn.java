/*
 * Copyright (c) 2009 IT - ITBA -- All rights reserved
 */
package ar.edu.itba.it.dev.common.web.datatable.columns;

import org.apache.wicket.Component;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.FilterForm;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;

import ar.edu.itba.it.dev.common.jpa.LocalDateRange;


/**
 * Column used to display dates, that provides filtering via a LocalDateRange object, 
 * allowing to specify an interval instead of a date
 * @param <T> Type of the row objects
 */
class DateRangeFilteredColumn<T> extends StyledFilterColumn<T> {

	
	public DateRangeFilteredColumn(ColumnBuilder<T> builder) {
		super(builder);
	}

	@Override
	public Component getFilter(String componentId, FilterForm<?> form) {
		return new DateRangePanel(componentId, getFilterModel(form), form);
	}

	protected IModel<LocalDateRange> getFilterModel(FilterForm<?> form) {
		return new PropertyModel<LocalDateRange>(form.getModel(), getFilterProperty());
	}
}

/*
 * Copyright (c) 2009 IT - ITBA -- All rights reserved
 */
package ar.edu.itba.it.dev.common.web.datatable.columns;

import org.apache.wicket.Component;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.FilterForm;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.joda.time.LocalDate;


class DateFilteredColumn<T> extends StyledFilterColumn<T> {

	DateFilteredColumn(ColumnBuilder<T> builder) {
		super(builder);
	}

	@Override
	public Component getFilter(String componentId, FilterForm<?> form) {
		DateTextFilter filter = new DateTextFilter(componentId, getFilterModel(form), form);
		return filter;
	}

	protected IModel<LocalDate> getFilterModel(FilterForm<?> form) {
		return new PropertyModel<LocalDate>(form.getModel(), getFilterProperty());
	}
}

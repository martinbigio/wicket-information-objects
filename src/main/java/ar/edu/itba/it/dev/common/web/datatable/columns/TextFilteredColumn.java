/*
 * Copyright (c) 2009 IT - ITBA -- All rights reserved
 */
package ar.edu.itba.it.dev.common.web.datatable.columns;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.FilterForm;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.TextFilter;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;

class TextFilteredColumn<T, F> extends StyledFilterColumn<T> {
	TextFilteredColumn(ColumnBuilder<T> builder) {
		super(builder);
	}
	
	@Override
	public Component getFilter(String componentId, FilterForm<?> form) {
		TextFilter<F> filter = new TextFilter<F>(componentId, getFilterModel(form), form);
		filter.getFilter().add(AttributeModifier.replace("style", "width:100%"));
		return filter;
	}
	
	private IModel<F> getFilterModel(FilterForm<?> form) {
		return new PropertyModel<F>(form.getModel(), getFilterProperty());
	}
}

/*
 * Copyright (c) 2008 IT - ITBA -- All rights reserved
 */ 
package ar.edu.itba.it.dev.common.web.datatable.columns;

import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.AbstractFilter;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.FilterForm;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.model.IModel;

/**
 * {@AbstractFilter} that displays a {@code CheckBox} for filtering boolean models.
 */
class CheckBoxFilter extends AbstractFilter {
	private final CheckBox filter;
	
	CheckBoxFilter(String id, IModel<Boolean> model, FilterForm<?> form) {
		super(id, form);
		filter = new CheckBox("filter", model);
		add(filter);
	}
	
	public CheckBox getFilter() {
		return filter;
	}
}

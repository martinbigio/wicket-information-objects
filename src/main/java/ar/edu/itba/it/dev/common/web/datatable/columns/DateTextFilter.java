/*
 * Copyright (c) 2008 IT - ITBA -- All rights reserved
 */ 
package ar.edu.itba.it.dev.common.web.datatable.columns;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.AbstractFilter;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.FilterForm;
import org.apache.wicket.model.IModel;
import org.joda.time.LocalDate;

import ar.edu.itba.it.dev.common.web.LocalDateTextField;

class DateTextFilter extends AbstractFilter {

	private final LocalDateTextField filter;
	
	public DateTextFilter(String id, IModel<LocalDate> model, FilterForm<?> form) {
		super(id, form);
		filter = new LocalDateTextField("filter", model);
		filter.add(AttributeModifier.replace("size", "10"));
		filter.add(AttributeModifier.replace("style", "width:11ex"));
		enableFocusTracking(filter);
		add(filter);
	}
	
	public LocalDateTextField getFilter() {
		return filter;
	}
}

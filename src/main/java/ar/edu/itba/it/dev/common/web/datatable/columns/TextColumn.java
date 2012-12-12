/*
 * Copyright (c) 2009 IT - ITBA -- All rights reserved
 */
package ar.edu.itba.it.dev.common.web.datatable.columns;

import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.util.convert.IConverter;

import ar.edu.itba.it.dev.common.web.datatable.IConvertableColumn;

/**
 * Convenience class for constructing PropertyColumns.
 */
class TextColumn<T> extends PropertyColumn<T> implements IConvertableColumn<T> {
	
	private String css;
	private IConverter customConverter; 

	@Override
	public String getCssClass() {
		return css;
	}
	
	TextColumn(ColumnBuilder<T> builder) {
		super(new ResourceModel(builder.getTitleKey()), builder.getSortProperty(), builder.getProperty());
		this.css = builder.getCss();
		this.customConverter = builder.getCustomConverter();
	}	
	
	@Override
	protected IModel<?> createLabelModel(IModel<T> rowModel) {
		if (customConverter == null) {
			return super.createLabelModel(rowModel);
		}
		else {
			return new ConverterModel(customConverter, super.createLabelModel(rowModel));
		}
	}

	@Override
	public IConverter getConverter() {
		return customConverter;
	}
}
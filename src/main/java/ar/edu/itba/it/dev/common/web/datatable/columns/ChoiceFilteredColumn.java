/*
 * Copyright (c) 2009 IT - ITBA -- All rights reserved
 */
package ar.edu.itba.it.dev.common.web.datatable.columns;

import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.Session;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.ChoiceFilter;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.ChoiceFilteredPropertyColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.FilterForm;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.util.convert.IConverter;

import ar.edu.itba.it.dev.common.web.datatable.IConvertableColumn;

/**
 * Column that displays a list of choices (combo) as filter value  
 *
 * @param <T> Type of column row object
 * @param <V> Type of choices 
 */
class ChoiceFilteredColumn<T, V> extends ChoiceFilteredPropertyColumn<T, V> implements IConvertableColumn<T>{
	
	private String filterProperty;
	private String css;
	private boolean nullValid;
	private List<CellDecorator<T>> cellDecorators;
	private IModel<V> model;
	private IConverter converter;

	@Override
	public String getCssClass() {
		return this.css;
	}
	
	ChoiceFilteredColumn(ColumnBuilder<T> builder, IModel<List<? extends V>> choices, boolean nullValid) {
		super(new ResourceModel(builder.getTitleKey()), builder.getSortProperty(), builder.getProperty(), choices);
		this.css = builder.getCss();
		this.filterProperty = builder.getFilterProperty();
		this.cellDecorators = builder.getCellDecorators();
		this.converter = builder.getCustomConverter();
		this.nullValid = nullValid;
	}

	@Override
	protected IModel<V> getFilterModel(FilterForm<?> form) {
		model = new PropertyModel<V>(form.getModel(), filterProperty);
		return model;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Component getFilter(String componentId, FilterForm<?> form) {
		ChoiceFilter<V> filter = (ChoiceFilter<V>) super.getFilter(componentId, form);
		filter.getChoice().setNullValid(nullValid);
		filter.getChoice().add(AttributeModifier.replace("style", "width:100%"));
		return filter;
	}
	
	@Override
	protected IChoiceRenderer<V> getChoiceRenderer() {
		if (converter == null) {
			return super.getChoiceRenderer();
		} else {
			return new IChoiceRenderer<V>() {
				@Override
				public Object getDisplayValue(V object) {
					return converter.convertToString(object, Session.get().getLocale());
				}

				@Override
				public String getIdValue(V object, int index) {
					return Integer.valueOf(index).toString();
				}
			};
		}
	}
	
	@Override
	public void populateItem(Item<ICellPopulator<T>> item, String componentId,
			IModel<T> rowModel) {
		super.populateItem(item, componentId, rowModel);
		if(!cellDecorators.isEmpty()) {
			
			for(CellDecorator<T> c : cellDecorators) {
				c.decorate(item, rowModel);
			}
		}
	}
	
	@Override
	protected IModel<?> createLabelModel(IModel<T> rowModel) {
		IModel<?> labelModel = getPropertyExpression() == null ? model : super.createLabelModel(rowModel);
		if (converter != null) {
			labelModel = new ConverterModel(converter, super.createLabelModel(rowModel));
		}
		return labelModel;
	}
	
	@Override
	protected boolean enableAutoSubmit() {
		return false;
	}

	@Override
	public IConverter getConverter() {
		return converter;
	}
}

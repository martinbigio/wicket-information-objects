/*
 * Copyright (c) 2009 IT - ITBA -- All rights reserved
 */
package ar.edu.itba.it.dev.common.web.datatable.columns;

import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;

import ar.edu.itba.it.dev.common.jpa.Introspectable;

/**
 * Convenience class for constructing PropertyColumns.
 */
public class IntrospectableColumn<T extends Introspectable> extends PropertyColumn<T> implements DynamicPropertyColumn<T> {
	
	private final String css;

	@Override
	public String getCssClass() {
		return css;
	}
	
	public IntrospectableColumn(String titleKey, String propertyExpression) {
		this(titleKey, propertyExpression, null);
	}
	
	public IntrospectableColumn(String propertyExpression) {
		this(propertyExpression, propertyExpression, null);
	}	

	public IntrospectableColumn(String titleKey, String propertyExpression, String css) {
		super(new ResourceModel(titleKey), propertyExpression);
		this.css = css;
	}
	
	@Override
	public IModel<?> createLabelModel(IModel<T> rowModel) {
		return new IntrospectablePropertyModel<T, Object>(rowModel, getPropertyExpression());
	}

	private static class IntrospectablePropertyModel<T extends Introspectable, E> extends AbstractReadOnlyModel<E> {

		private final IModel<T> target;
		private final String propertyExpression;
		
		IntrospectablePropertyModel(IModel<T> target, String propertyExpression) {
			this.target = target;
			this.propertyExpression = propertyExpression;
		}

		@SuppressWarnings("unchecked")
		@Override
		public E getObject() {
			T target = getTarget();
			return (E) target.getValue(propertyExpression);
		}
		
		@SuppressWarnings("unchecked")
		public final T getTarget()
		{
			Object object = target;
			while (object instanceof IModel)
			{
				Object tmp = ((IModel<?>)object).getObject();
				if (tmp == object)
				{
					break;
				}
				object = tmp;
			}
			return (T) object;
		}
	}
}
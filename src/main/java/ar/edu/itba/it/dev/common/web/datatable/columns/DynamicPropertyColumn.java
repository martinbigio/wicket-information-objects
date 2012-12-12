package ar.edu.itba.it.dev.common.web.datatable.columns;

import org.apache.wicket.model.IModel;

public interface DynamicPropertyColumn<T> {
	
	public IModel<?> createLabelModel(IModel<T> rowModel);
}

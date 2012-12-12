package ar.edu.itba.it.dev.common.web;

import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

/**
 * Wicket panel augmented with type safe model generics
 * @author Pablo Abad (pabad@itba.edu.ar)
 * @param <T> Type of the model
 */
public class TypedPanel<T> extends Panel {

	public TypedPanel(String id) {
		super(id);
	}

	public TypedPanel(String id, IModel<? extends T> model) {
		super(id, model);
	}

	public void setModel(IModel<T> model) {
		setDefaultModel(model);
	}
	
	public void setModelObject(T obj) {
		getModel().setObject(obj);
	}
	
	@SuppressWarnings("unchecked")
	public IModel<T> getModel() {
		return (IModel<T>) getDefaultModel();
	}
	
	@SuppressWarnings("unchecked")
	public T getModelObject() {
		return (T) getDefaultModelObject();
	}
}

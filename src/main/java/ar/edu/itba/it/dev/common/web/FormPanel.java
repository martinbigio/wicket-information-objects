package ar.edu.itba.it.dev.common.web;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;

/**
 * A panel that is placed inside a form
 * @param <T>
 */
public class FormPanel<T> extends TypedPanel<T> {
	private Form<?> enclosing;
	
	public FormPanel(String id, Form<?> enclosing) {
		super(id);
		this.enclosing = enclosing;
	}

	public FormPanel(String id, IModel<T> model, Form<?> enclosing) {
		super(id, model);
		this.enclosing = enclosing;
	}
	
	public Form<?> getEnclosingForm() {
		return enclosing;
	}
}

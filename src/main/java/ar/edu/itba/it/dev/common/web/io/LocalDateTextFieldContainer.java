package ar.edu.itba.it.dev.common.web.io;

import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.model.IModel;

import ar.edu.itba.it.dev.common.web.LocalDateTextField;

public class LocalDateTextFieldContainer extends TextFieldContainer {

	public LocalDateTextFieldContainer(String id, Class<?> clazz, IModel<?> model) {
		super(id, clazz, model);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected FormComponent<?> getComponent() {
		return new LocalDateTextField("field", getModel());
	}
}
package ar.edu.itba.it.dev.common.web.io;

import org.apache.wicket.markup.html.form.FormComponentPanel;
import org.apache.wicket.model.IModel;

import ar.edu.itba.it.dev.common.web.io.FormComponentLibrary.FormComponentProvider;

public class EmptyContainerFormComponentProvider implements FormComponentProvider {

	@Override
	public FormComponentPanel<?> get(String id, Class<?> clazz, IModel<?> model) {
		return new EmptyFormComponentPanel(id);
	}
}
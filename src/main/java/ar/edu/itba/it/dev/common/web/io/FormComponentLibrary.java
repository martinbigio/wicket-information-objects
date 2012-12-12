package ar.edu.itba.it.dev.common.web.io;

import org.apache.wicket.markup.html.form.FormComponentPanel;
import org.apache.wicket.model.IModel;

import com.google.common.base.Optional;

public interface FormComponentLibrary {
	
	/**
	 * Retrieves the form component type that should be used for editing the given class.
	 */
	Optional<FormComponentProvider> forClass(Class<?> clazz);
	
	public interface FormComponentProvider {
		FormComponentPanel<?> get(String id, Class<?> clazz, IModel<?> model);
	}
}
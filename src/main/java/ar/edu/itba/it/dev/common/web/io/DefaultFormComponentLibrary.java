package ar.edu.itba.it.dev.common.web.io;

import java.util.HashMap;
import java.util.Map;

import org.apache.wicket.markup.html.form.FormComponentPanel;
import org.apache.wicket.model.IModel;
import org.joda.time.LocalDate;

import com.google.common.base.Optional;

import ar.edu.itba.it.dev.io.backend.domain.PersonIO;
import ar.edu.itba.it.dev.io.backend.domain.SubjectIO;

public class DefaultFormComponentLibrary implements FormComponentLibrary {

	private Map<Class<?>, FormComponentProvider> providers;
	
	public DefaultFormComponentLibrary() {
		providers = new HashMap<Class<?>, FormComponentProvider>();
		providers.put(String.class, new TextFieldProvider());
		providers.put(Integer.class, new TextFieldProvider());
		providers.put(PersonIO.class, new PersonTextFieldProvider());
		providers.put(SubjectIO.class, new SubjectTextFieldProvider());
		providers.put(LocalDate.class, new LocalDateTextFieldProvider());
		providers.put(Enum.class, new EnumProvider());
	}
	
	@Override
	public Optional<FormComponentProvider> forClass(Class<?> clazz) {
		Class<?> curr = clazz;
		while (!curr.equals(Object.class)) {
			FormComponentProvider provider = providers.get(curr);
			if (provider != null) {
				return Optional.of(provider);
			}
			curr = curr.getSuperclass();
		}
		return Optional.absent();
	}

	public void register(Class<?> clazz, FormComponentProvider provider) {
		providers.put(clazz, provider);
	}
	
	private static class TextFieldProvider implements FormComponentProvider {
		
		@Override
		public FormComponentPanel<?> get(String id, Class<?> clazz, IModel<?> model) {
			return new TextFieldContainer(id, clazz, model);
		}
	}
	
	private static class PersonTextFieldProvider implements FormComponentProvider {

		@Override
		public FormComponentPanel<?> get(String id, Class<?> clazz, IModel<?> model) {
			return new PersonTextFieldContainer(id, clazz, model);
		}
	}
	
	private static class SubjectTextFieldProvider implements FormComponentProvider {

		@Override
		public FormComponentPanel<?> get(String id, Class<?> clazz, IModel<?> model) {
			return new SubjectTextFieldContainer(id, clazz, model);
		}
	}
	
	private static class LocalDateTextFieldProvider implements FormComponentProvider {

		@Override
		public FormComponentPanel<?> get(String id, Class<?> clazz, IModel<?> model) {
			return new LocalDateTextFieldContainer(id, clazz, model);
		}
	}

	
	private static class EnumProvider implements FormComponentProvider {
		@Override
		public FormComponentPanel<?> get(String id, Class<?> clazz, IModel<?> model) {
			return new EnumDropDownChoiceContainer(id, clazz, model);
		}
	}
}
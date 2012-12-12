package ar.edu.itba.it.dev.common.web.io;

import org.apache.wicket.MarkupContainer;
import org.apache.wicket.markup.DefaultMarkupResourceStreamProvider;
import org.apache.wicket.markup.IMarkupResourceStreamProvider;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.resource.IResourceStream;

public class TextFieldContainer extends FormComponentContainer implements IMarkupResourceStreamProvider {

	public TextFieldContainer(String id, Class<?> clazz, IModel<?> model) {
		super(id, clazz, model);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected FormComponent<?> getComponent() {
		return new TextField("field", getModel(), getClazz());
	}

	@Override
	public IResourceStream getMarkupResourceStream(MarkupContainer container, Class<?> containerClass) {
		return new DefaultMarkupResourceStreamProvider().getMarkupResourceStream(container, TextFieldContainer.class);
	}
}
package ar.edu.itba.it.dev.common.web.io;

import org.apache.wicket.MarkupContainer;
import org.apache.wicket.markup.DefaultMarkupResourceStreamProvider;
import org.apache.wicket.markup.IMarkupResourceStreamProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.resource.IResourceStream;

public abstract class DropDownChoiceContainer extends FormComponentContainer implements IMarkupResourceStreamProvider {

	public DropDownChoiceContainer(String id, Class<?> clazz, IModel<?> model) {
		super(id, clazz, model);
	}

	@Override
	public IResourceStream getMarkupResourceStream(MarkupContainer container, Class<?> containerClass) {
		return new DefaultMarkupResourceStreamProvider().getMarkupResourceStream(container, DropDownChoiceContainer.class);
	}
}
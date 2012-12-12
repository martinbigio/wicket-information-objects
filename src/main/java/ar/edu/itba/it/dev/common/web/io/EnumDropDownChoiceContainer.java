package ar.edu.itba.it.dev.common.web.io;

import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.model.IModel;

import ar.edu.itba.it.dev.common.web.EnumValuesModel;

public class EnumDropDownChoiceContainer extends DropDownChoiceContainer {

	public EnumDropDownChoiceContainer(String id, Class<?> clazz, IModel<?> model) {
		super(id, clazz, model);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected FormComponent<?> getComponent() {
		Class<? extends Enum<?>> clazz = (Class<? extends Enum<?>>) getClazz();
		return new DropDownChoice("field", getModel(), EnumValuesModel.create(clazz));
	}
}
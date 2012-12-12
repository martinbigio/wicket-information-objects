package ar.edu.itba.it.dev.common.web.io;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.FormComponentPanel;
import org.apache.wicket.model.IModel;

import com.google.common.base.Preconditions;

@SuppressWarnings("rawtypes")
public abstract class FormComponentContainer extends FormComponentPanel {

	private Class<?> clazz;
	private FormComponent<?> component;

	@SuppressWarnings("unchecked")
	public FormComponentContainer(String id, Class<?> clazz, IModel<?> model) {
		super(id, model);
		this.clazz = Preconditions.checkNotNull(clazz);
		component = getComponent();
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();
		add(component.setRequired(isRequired()));
	}

	@Override
	public Component add(Behavior... behaviors) {
		component.add(behaviors);
		return this;
	}

	@SuppressWarnings("unchecked")
	@Override
	public FormComponent setLabel(IModel labelModel) {
		component.setLabel(labelModel);
		return this;
	}
	
	@Override
	public void updateModel() {
		// do nothing cause model is update by the inner form component
	}
	
	public Class<?> getClazz() {
		return clazz;
	}
	
	protected abstract FormComponent<?> getComponent();
}
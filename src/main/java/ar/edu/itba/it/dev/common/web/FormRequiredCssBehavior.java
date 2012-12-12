package ar.edu.itba.it.dev.common.web;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.form.FormComponent;

public class FormRequiredCssBehavior extends AbstractCssAttributeBehavior {
	private static final String FORM_ELEMENT_REQUIRED_CSS = "required";

	@Override
	protected String getCssClass(Component component) {
		FormComponent<?> value = (FormComponent<?>) component;
		return value.isRequired() ? FORM_ELEMENT_REQUIRED_CSS : null; 
	}

	@Override
	public boolean isEnabled(Component component) {
		return component instanceof FormComponent<?>;
	}
}

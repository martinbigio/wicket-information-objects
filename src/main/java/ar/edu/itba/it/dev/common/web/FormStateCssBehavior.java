package ar.edu.itba.it.dev.common.web;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.form.FormComponent;

public class FormStateCssBehavior extends AbstractCssAttributeBehavior {
	
	private static final String FORM_ELEMENT_VALID_CSS = "valid";
	private static final String FORM_ELEMENT_INVALID_CSS = "error";

	@Override
	public boolean isEnabled(Component component) {
		return component instanceof FormComponent<?>;
	}

	@Override
	protected String getCssClass(Component component) {
		FormComponent<?> value = (FormComponent<?>) component;
		return value.isValid() ? FORM_ELEMENT_VALID_CSS : FORM_ELEMENT_INVALID_CSS; 
	}
}

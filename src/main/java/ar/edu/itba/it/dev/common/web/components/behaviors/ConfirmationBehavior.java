/*
 * Copyright (c) 2009 IT - ITBA -- All rights reserved
 */
package ar.edu.itba.it.dev.common.web.components.behaviors;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.model.IModel;

/**
 * Behaviour that adds client side confirmation before clicking on a component
 */
public class ConfirmationBehavior extends AttributeModifier {
	private static final String JS_CONFIRM = "if (confirm('%s')) { %s }; return false;";
	private static final String JS_DEFAULT = "return true;";
	private static final long serialVersionUID = 1L;

	public ConfirmationBehavior(IModel<?> question) {
		super("onclick", question);
	}


	@Override
	protected String newValue(String currentValue, String replacementValue) {
       	return String.format(JS_CONFIRM, replacementValue, currentValue == null ? JS_DEFAULT : currentValue);
	}
}

/*
 * Copyright (c) 2009 IT - ITBA -- All rights reserved
 */
package ar.edu.itba.it.dev.common.web;

import org.apache.wicket.Application;
import org.apache.wicket.Component;
import org.apache.wicket.Localizer;
import org.apache.wicket.Session;
import org.apache.wicket.model.IModel;

/**
 * Localizer that attempts to resolve property expressions converting types via registered converters at
 * the application level
 */
public class ConverterAwareLocalizer extends Localizer {

	@Override
	public String substitutePropertyExpressions(Component component, String string, IModel<?> model) {
		if (string != null && model != null) {
			return PropertyConverterVariableInterpolator.interpolate(string, model.getObject(), Application.get().getConverterLocator(), Session.get().getLocale());
		}
		return string;
	}
}

/*
 * Copyright (c) 2009 IT - ITBA -- All rights reserved
 */
package ar.edu.itba.it.dev.common.web;

import java.util.Locale;

import org.apache.wicket.IConverterLocator;
import org.apache.wicket.util.convert.IConverter;
import org.apache.wicket.util.lang.PropertyResolver;
import org.apache.wicket.util.string.interpolator.VariableInterpolator;

/**
 * Variable interpolator that attempts resolves properties but attempts to convert the
 * returned object via registered converters BEFORE interpolation
 *
 */
public class PropertyConverterVariableInterpolator extends VariableInterpolator {
	/** The model to introspect on */
	private final Object model;
	private final IConverterLocator locator;
	private final Locale locale;

	/**
	 * Private constructor to force use of static interpolate method.
	 * 
	 * @param string
	 *            a <code>String</code> to interpolate into
	 * @param model
	 *            the model to apply property expressions to
	 */
	private PropertyConverterVariableInterpolator(final String string, final Object model, IConverterLocator locator, Locale locale) {
		super(string);
		this.model = model;
		this.locator = locator;
		this.locale = locale;
	}
	

	@SuppressWarnings("unchecked")
	@Override
	protected String getValue(final String variableName) {
		Object value = PropertyResolver.getValue(variableName, model);
		if (value != null) {
			@SuppressWarnings("rawtypes")
			IConverter converter = locator.getConverter(value.getClass());
			return converter == null ? value.toString() : converter.convertToString(value, locale);
		}
		return null;
	}
	
	
	/**
	 * Interpolates the given <code>String</code>, substituting values for property expressions.
	 * 
	 * @param string
	 *            a <code>String</code> containing property expressions like <code>${xyz}</code>
	 * @param object
	 *            the <code>Object</code> to reflect on
	 * @return the interpolated <code>String</code>
	 */
	public static String interpolate(final String string, final Object object, IConverterLocator locator, Locale locale) {
		// If there's any reason to go to the expense of property expressions
		if (string.indexOf("${") != -1) {
			// Do property expression interpolation
			return new PropertyConverterVariableInterpolator(string, object, locator, locale).toString();
		}

		// Return simple string
		return string;
	}
}

/*
 * Copyright (c) 2009 IT - ITBA -- All rights reserved
 */
package ar.edu.itba.it.dev.common.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.extensions.ajax.markup.html.autocomplete.AutoCompleteSettings;
import org.apache.wicket.extensions.ajax.markup.html.autocomplete.AutoCompleteTextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.convert.IConverter;
import org.apache.wicket.validation.IValidator;

/**
 * Auto complete component that has set the CSS and is prepared for AJAX.
 * It has defaults values for the min amount of characters needed for
 * doing the search and the max amount of results retrieved. 
 */
public abstract class LimitedAutoCompleteTextField<T> extends AutoCompleteTextField<T> {

	public static final int MINAUTOCOMPLETEINPUTLENGHT = 2;
	public static int MAXAUTOCOMPLETEROWS = 50;
	
	private int minLimit;
	private int maxLimit;

	public LimitedAutoCompleteTextField(String id, Class<T> type) {
		this(id, null, type);
	}
	
	/**
	 * @param id of the component
	 * @param model of the component
	 */
	public LimitedAutoCompleteTextField(String id, IModel<T> model, Class<T> type) {
		this(id, model, type, null);
	}
	
	/**
	 * @param id of the component
	 * @param model of the component
	 * @param required true if the value is required
	 * @param validator of the component
	 */
	public LimitedAutoCompleteTextField(String id, IModel<T> model, Class<T> type, IValidator<T> validator) {
		super(id, model, type, new BoldedStringAutoCompleteRenderer<T>(), new AutoCompleteSettings());

		setOutputMarkupId(true);
		if (validator != null) {
			add(validator);
		}
		
		this.minLimit = LimitedAutoCompleteTextField.MINAUTOCOMPLETEINPUTLENGHT;
		this.maxLimit = LimitedAutoCompleteTextField.MAXAUTOCOMPLETEROWS;
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected Iterator<T> getChoices(String input) {
		List<Serializable> results = new ArrayList<Serializable>();
		
		if (input.length() < this.minLimit) {
			results.add( getLocalizer().getString("search.parameter.too.short", this, "PARAMETER TOO SHORT"));
		}
		else {
			for(T result : getResults(input)) {
				if(results.size() < this.maxLimit) {
					results.add(getConverter(super.getType()).convertToString(result, getLocale()));
				}
				else {
					break;
				}
			}
		}
		if(results.size() >= this.maxLimit) {
			results.add(getLocalizer().getString("too.many.results", this, "TOO MANY RESULTS"));
		}
		return (Iterator<T>) results.iterator();
	}

	/**
	 * Sets the new values of the min required characters and the max results
	 * @param minLimit of the required characters
	 * @param maxLimit of the retrieved results
	 */
	public void setLimits(int minLimit, int maxLimit) {
		this.minLimit = minLimit;
		this.maxLimit = maxLimit;
	}
	
	public void setMinLimit(int minLimit) {
		this.minLimit = minLimit;
	}

	public void setMaxLimit(int maxLimit) {
		this.maxLimit = maxLimit;
	}
	
	public int getMaxLimit() {
		return maxLimit;
	}
	
	public int getMinLimit() {
		return minLimit;
	}
	
	/**
	 * Concrete sub class must implement the method that makes the search filtering by the
	 * user input
	 * @param input
	 * @return
	 */
	public abstract Iterable<T> getResults(String input);
	
	
	@Override
	@SuppressWarnings("unchecked")
	public final <C> IConverter<C> getConverter(Class<C> type) {
		if (getType().equals(type)) {
			return (IConverter<C>) getConverter();
		} else {
			return super.getConverter(type);
		}
	}
	
	/**
	 * Provides a converter to use in this component. Override to use a custom converter.
	 */
	protected IConverter<T> getConverter() {
		return super.getConverter(getType());
	}
}

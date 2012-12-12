/*
 * Copyright (c) 2009 IT - ITBA -- All rights reserved
 */
package ar.edu.itba.it.dev.common.web.components.behaviors;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.util.string.Strings;

/**
 * Behavior that adds a css class attribute to the component.
 * <p>
 * usign this behavior is preferred to setting directly the class attribute, as
 * this bahavior takes care of the case where an existing class was in place
 * </p>
 * <p>
 * Child classes are expected to override getCssClass to provide their own
 * class name 
 * </p>
 */
public abstract class AbstractCssAttributeBehavior extends Behavior	{

	/**
	 * Returns the css class that should be added to the component.
	 * @param component Component that will recive the class
	 * @return 
	 */
	protected abstract String getCssClass(Component component);

	/* (non-Javadoc)
	 * @see org.apache.wicket.behavior.AbstractBehavior#onComponentTag(org.apache.wicket.Component, org.apache.wicket.markup.ComponentTag)
	 */
	@Override
	public final void onComponentTag(Component component, ComponentTag tag) {
		String className = getCssClass(component);
		if (!Strings.isEmpty(className)) {
			CharSequence oldClassName = tag.getAttribute("class");
			if (Strings.isEmpty(oldClassName)) {
				tag.put("class", className);
			}
			else {
				tag.put("class", oldClassName + " " + className);
			}
		}
	}
}

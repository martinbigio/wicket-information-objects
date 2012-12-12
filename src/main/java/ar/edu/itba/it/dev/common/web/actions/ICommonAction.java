/*
 * Copyright (c) 2009 IT - ITBA -- All rights reserved
 */
package ar.edu.itba.it.dev.common.web.actions;

import org.apache.wicket.Component;
import org.apache.wicket.model.IModel;

/**
 * An action that may be executed
 */
public interface ICommonAction<T>  extends IAction<T> {
	
	/**
	 * Called when the action needs to be executed
	 * <p>
	 * For example, processing may trigger loading another page:
	 * 		component.setResponsePage(...);
	 * </p>
	 * @param component the component that held the action
	 * @param data optional model associated with the action
	 */
	public void onExecute(Component component, IModel<T> data);
}

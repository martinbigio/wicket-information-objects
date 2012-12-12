/*
 * Copyright (c) 2009 IT - ITBA -- All rights reserved
 */
package ar.edu.itba.it.dev.common.web.actions;

import org.apache.wicket.IClusterable;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.resource.ResourceReference;

/**
 * An action that may be executed
 */
public interface IAction<T>  extends IClusterable {
	/**
	 * Model that resolves to the caption of the action
	 * @param data optional model associated with the action 
	 * @return A string model that can be used to list the action
	 */
	public IModel<String> getCaption(IModel<T> data);
	
	/**
	 * Model that resolves to the description of the actions
	 * @param data optional model associated with the action
	 * @return A string model that provides an explanation of the action. May be null
	 */
	public IModel<String> getDescription(IModel<T> data);
	
	
	/**
	 * Model that resolves to the confirmation message. 
	 * If the model is null no confirmation will be asked before executing the action
	 * @param data data optional model associated with the actions
	 * @return A String model that provides the confirmation message
	 */
	public IModel<String> getConfirmationMessage(IModel<T> data);
	
	/**
	 * Returns an image resource that can be used to represent the action
	 * @param data optional model associated with the action 
	 * @return An image resource
	 */
	public ResourceReference getImage(IModel<T> data);

	/**
	 * Returns true if the action should be displayed
	 * @param data optional model associated with the action 
	 * @return True if the action has to be displayed (i.e.: it is available), false otherwise
	 */
	public boolean isVisible(IModel<T> data);
	
	/**
	 * Returns true if the action can executed by current user.
	 * This should return true if the user can theoretically perform this
	 * action for a given data model.
	 */
	public boolean canExecute();
}

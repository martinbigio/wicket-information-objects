package ar.edu.itba.it.dev.common.web.components.events;

import java.io.Serializable;

import org.apache.wicket.Component;

public interface ComponentEventListener extends Serializable{

	/**
	 * Method called once when creating the control. This method
	 * can be used to add child components to the control
	 * <p>It is not necessary to call super.onInitialize()</p>
	 */
	public void onInitialize(Component component);

	/**
	 * Method called once per rendering. This method
	 * can and should set visibility or enabled status 
	 * <p>It is not necessary to call super.onConfigure()</p>
	 */
	public void onConfigure(Component component);

	/**
	 * Method called after rendering. This method
	 * may be called multiple times. 
	 * <p>It is not necessary to call super.onDetach()</p>
	 */
	public void onDetach(Component component);

}
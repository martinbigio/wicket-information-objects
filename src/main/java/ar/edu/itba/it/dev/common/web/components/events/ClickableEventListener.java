package ar.edu.itba.it.dev.common.web.components.events;

import org.apache.wicket.Component;

/**
 * interface used to add functionality to clickable controls
 * <p>Note that this interface should usually not be instantieated.
 * Instead use {@link ClickableEventAdapter} and override only
 * the needed methods</p>
 */
public interface ClickableEventListener extends ComponentEventListener {
	/**
	 * Callback method called for click events
	 * @param component
	 */
	public void onClick(Component component);
}

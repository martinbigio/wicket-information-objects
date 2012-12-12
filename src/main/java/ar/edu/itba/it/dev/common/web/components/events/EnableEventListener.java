package ar.edu.itba.it.dev.common.web.components.events;

/**
 * Inteface that provides a listener for enable/disable events
 */
@Deprecated
public interface EnableEventListener extends EventListener {

	/**
	 * Callback method called for enable/disable events
	 */
	public boolean isEnabled();
	
}

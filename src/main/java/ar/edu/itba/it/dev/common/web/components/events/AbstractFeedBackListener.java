package ar.edu.itba.it.dev.common.web.components.events;

import org.apache.wicket.Component;
import org.apache.wicket.Page;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.Model;

import ar.edu.itba.it.dev.common.jpa.ApplicationException;

/**
 * Base class that provides common methods for automatic feedback notification
 * on exceptions
 * @author Pablo Abad (pabad@itba.edu.ar)
 */
public class AbstractFeedBackListener {

	protected void reportError(Component component, String message) {
		if (message != null) {
			component.error(message);
		}
	}
	
	protected void reportWarning(Component component, String message) {
		if (message != null) {
			component.warn(message);
		}
	}
	
	protected void reportError(Component component, ApplicationException ex) {
		String msg = component.getString(ex.getClass().getSimpleName(), new Model<ApplicationException>(ex));
		reportError(component, msg);
	}
	
	protected void addFeedbackPanel(Page page, AjaxRequestTarget target) {
		if (page instanceof FeedbackPanelLocator) {
			target.add(((FeedbackPanelLocator)page).getFeedbackPanel());
		}		
	}
}

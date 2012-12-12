package ar.edu.itba.it.dev.common.web.components.events;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;

import ar.edu.itba.it.dev.common.jpa.ApplicationException;
import ar.edu.itba.it.dev.common.web.components.events.ajax.AjaxClickableEventListener;

public class FeedbackAjaxClickableEventListener extends FeedbackComponentEventListener<AjaxClickableEventListener> implements AjaxClickableEventListener {
	
	public FeedbackAjaxClickableEventListener(AjaxClickableEventListener delegate) {
		super(delegate);
	}

	@Override
	public final void onClick(Component component, AjaxRequestTarget target) {
		try {
			delegate().onClick(component, target);
		}
		catch (ApplicationException ex) {
			reportError(component, ex);
			addFeedbackPanel(component.getPage(), target);
		}
	}
}

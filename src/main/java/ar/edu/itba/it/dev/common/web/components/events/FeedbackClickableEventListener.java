package ar.edu.itba.it.dev.common.web.components.events;

import org.apache.wicket.Component;

import ar.edu.itba.it.dev.common.jpa.ApplicationException;

public class FeedbackClickableEventListener extends FeedbackComponentEventListener<ClickableEventListener> implements ClickableEventListener {
	private static final long serialVersionUID = 1L;
	
	public FeedbackClickableEventListener(ClickableEventListener delegate) {
		super(delegate);
	}

	@Override
	public void onClick(Component component) {
		try {
			delegate().onClick(component);
		}
		catch(ApplicationException ex) {
			reportError(component, ex);
		}
	}
}

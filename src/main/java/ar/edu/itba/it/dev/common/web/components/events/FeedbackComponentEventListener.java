package ar.edu.itba.it.dev.common.web.components.events;

import org.apache.wicket.Component;

import ar.edu.itba.it.dev.common.jpa.ApplicationException;

import com.google.common.base.Preconditions;

public class FeedbackComponentEventListener<T extends ComponentEventListener> extends AbstractFeedBackListener implements ComponentEventListener {
	private static final long serialVersionUID = 1L;
	
	private T delegate;

	public FeedbackComponentEventListener(T delegate) {
		super();
		Preconditions.checkNotNull(delegate);
		this.delegate = delegate;
	}
	
	protected T delegate() {
		return delegate;
	}
	
	@Override
	public void onConfigure(Component component) {
		try {
			delegate.onConfigure(component);
		}
		catch(ApplicationException ex) {
			reportError(component, ex);
		}
	}
	
	@Override
	public void onDetach(Component component) {
		try {
			delegate.onDetach(component);
		}
		catch(ApplicationException ex) {
			reportError(component, ex);
		}
	}
	
	@Override
	public void onInitialize(Component component) {
		try {
			delegate.onInitialize(component);
		}
		catch(ApplicationException ex) {
			reportError(component, ex);
		}
	}
}

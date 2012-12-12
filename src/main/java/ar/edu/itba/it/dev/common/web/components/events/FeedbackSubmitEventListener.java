package ar.edu.itba.it.dev.common.web.components.events;

import org.apache.wicket.markup.html.form.Form;

import ar.edu.itba.it.dev.common.jpa.ApplicationException;

public class FeedbackSubmitEventListener extends AbstractFeedBackListener implements SubmitEventListener {
	private static final long serialVersionUID = 1L;
	private SubmitEventListener delegate;
	
	public FeedbackSubmitEventListener(SubmitEventListener delegate) {
		super();
		this.delegate = delegate;
	}

	@Override
	public void onError(Form<?> form) {
		try {
			delegate.onSubmit(form);
		}
		catch(ApplicationException ex) {
			reportError(form, ex);
		}
	}

	@Override
	public void onSubmit(Form<?> form) {
		try {
			delegate.onError(form);
		}
		catch(ApplicationException ex) {
			reportError(form, ex);
		}
	}
}

package ar.edu.itba.it.dev.common.web.components.events;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;

import ar.edu.itba.it.dev.common.jpa.ApplicationException;
import ar.edu.itba.it.dev.common.web.components.events.ajax.AjaxSubmitEventListener;

public class FeedbackAjaxSubmitEventListener extends AbstractFeedBackListener implements AjaxSubmitEventListener {
	private static final long serialVersionUID = 1L;
	private AjaxSubmitEventListener delegate;
	
	public FeedbackAjaxSubmitEventListener(AjaxSubmitEventListener delegate) {
		super();
		this.delegate = delegate;
	}

	@Override
	public void onError(Form<?> form, AjaxRequestTarget target) {
		try {
			delegate.onSubmit(form, target);
		}
		catch(ApplicationException ex) {
			reportError(form, ex);
			addFeedbackPanel(form.getPage(), target);
		}
	}

	@Override
	public void onSubmit(Form<?> form, AjaxRequestTarget target) {
		try {
			delegate.onError(form, target);
		}
		catch(ApplicationException ex) {
			reportError(form, ex);
			addFeedbackPanel(form.getPage(), target);
		}
	}
}

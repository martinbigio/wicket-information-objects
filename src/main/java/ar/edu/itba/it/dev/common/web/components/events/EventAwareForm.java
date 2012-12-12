package ar.edu.itba.it.dev.common.web.components.events;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;

public class EventAwareForm<T> extends Form<T> {
	private static final long serialVersionUID = 1L;
	private SubmitEventListener listener;

	public EventAwareForm(String id, IModel<T> model, SubmitEventListener listener) {
		super(id, model);
		this.listener = listener;
	}
	
	public EventAwareForm(String id, IModel<T> model) {
		super(id, model);
	}

	public EventAwareForm(String id) {
		super(id);
	}
	
	@Override
	protected final void onSubmit() {
		if (listener != null) {
			listener.onSubmit(this);
		}
	}
	
	@Override
	protected final void onError() {
		if (listener != null) {
			listener.onError(this);
		}
	}
}

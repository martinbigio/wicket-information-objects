package ar.edu.itba.it.dev.common.web.components.events.ajax;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.ajax.markup.html.IndicatingAjaxButton;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;

/**
 * Ajax button that pushes event to the registered listener
 * @author Pablo Abad (pabad@itba.edu.ar)
 */
public class EventAwareAjaxIndicatingButton extends IndicatingAjaxButton {
	private AjaxClickableEventListener listener;
	
	public EventAwareAjaxIndicatingButton(String id, AjaxClickableEventListener listener) {
		super(id);
		this.listener = listener;
	}

	public EventAwareAjaxIndicatingButton(String id, IModel<String> model, AjaxClickableEventListener listener) {
		super(id);
		setModel(model);
		this.listener = listener;
	}

	@Override
	protected final void onSubmit(AjaxRequestTarget target, Form<?> form) {
		/* The feedback panel should be add also when no error occurred for cleaning previous error messages.
		 * The add should go before the delegate#onClick because it may change the page hierarchy removing the component. */
		EventUtils.addFeedbackPanel(this, target);
		listener.onClick(this, target);
	}
	
	@Override
	protected void onError(AjaxRequestTarget target, Form<?> form) {
		EventUtils.addFeedbackPanel(this, target);
		target.add(form);
	}
	
	@Override
	protected void onInitialize() {
		super.onInitialize();
		listener.onInitialize(this);
	}
	
	@Override
	protected void onConfigure() {
		super.onConfigure();
		listener.onConfigure(this);
	}
	
	@Override
	protected void onDetach() {
		listener.onDetach(this);
		super.onDetach();
	}
	
}

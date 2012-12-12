package ar.edu.itba.it.dev.common.web.components.events;

import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.model.IModel;

/**
 * Button that handles click action via a listener instead of subclassing
 * @author Pablo Abad (pabad@itba.edu.ar)
 */
public class EventAwareButton extends Button {
	private static final long serialVersionUID = 1L;
	private ClickableEventListener listener;

	public EventAwareButton(String id, ClickableEventListener listener) {
		super(id);
		this.listener = listener;
	}

	public EventAwareButton(String id, IModel<String> model, ClickableEventListener listener) {
		super(id, model);
		this.listener = listener;
	}
	
	@Override
	public final void onSubmit() {
		listener.onClick(this);
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

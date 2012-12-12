package ar.edu.itba.it.dev.common.web.components.events;

import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.IModel;

/**
 * Link that handles click action via a listener instead of subclassing
 * @param <T>
 */
public class EventAwareLink<T> extends Link<T> {
	private static final long serialVersionUID = 1L;
	private ClickableEventListener listener;

	public EventAwareLink(String id, ClickableEventListener listener) {
		super(id);
		this.listener = listener;
	}

	public EventAwareLink(String id, IModel<T> model, ClickableEventListener listener) {
		super(id, model);
		this.listener = listener;
	}

	@Override
	public final void onClick() {
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

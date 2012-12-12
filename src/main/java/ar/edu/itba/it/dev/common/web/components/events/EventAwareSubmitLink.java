package ar.edu.itba.it.dev.common.web.components.events;

import org.apache.wicket.markup.html.form.SubmitLink;

/**
 * Submit link that handles click action via a listener instead of subclassing
 * @param <T>
 */
public class EventAwareSubmitLink extends SubmitLink {
	private static final long serialVersionUID = 1L;
	private ClickableEventListener listener;

	public EventAwareSubmitLink(String id, ClickableEventListener listener) {
		super(id);
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

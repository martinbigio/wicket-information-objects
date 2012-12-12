package ar.edu.itba.it.dev.common.web.components.events.ajax;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.model.IModel;

public class EventAwareAjaxLink<T> extends AjaxLink<T> {
	private static final long serialVersionUID = 1L;
	private AjaxClickableEventListener listener; 

	public EventAwareAjaxLink(String id, AjaxClickableEventListener listener) {
		super(id);
		this.listener = listener;
	}
	
	public EventAwareAjaxLink(String id, IModel<T> model, AjaxClickableEventListener listener) {
		super(id, model);
		this.listener = listener;
	}

	@Override
	public final void onClick(AjaxRequestTarget target) {
		listener.onClick(this, target);
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

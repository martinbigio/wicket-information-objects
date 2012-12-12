package ar.edu.itba.it.dev.common.web.components.events.ajax;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.markup.html.form.Form;

public class EventAwareAjaxSubmitLink extends AjaxSubmitLink {
	private static final long serialVersionUID = 1L;
	private AjaxClickableEventListener listener; 

	public EventAwareAjaxSubmitLink(String id, AjaxClickableEventListener listener) {
		super(id);
		this.listener = listener;
	}
	
	@Override
	protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
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

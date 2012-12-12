package ar.edu.itba.it.dev.common.web.components.events.ajax;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;

import ar.edu.itba.it.dev.common.web.components.events.ComponentEventListener;

public interface AjaxClickableEventListener extends ComponentEventListener {
	public void onClick(Component component, AjaxRequestTarget target);
}

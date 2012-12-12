package ar.edu.itba.it.dev.common.web.components.events.ajax;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;

import ar.edu.itba.it.dev.common.web.components.events.EventListener;

/**
 * Inteface that provides listeners for ajax event postprocessing.
 * These events are tied to setting the ajax request target to the appropriate
 * values depending on the result of the operation
 *  
 * @author Pablo Abad (pabad@itba.edu.ar)
 */
public interface AjaxSubmitEventListener extends EventListener {
	/**
	 * Callback method called after an ajax event has been successfully executed 
	 * @param component
	 * @param target
	 */
	public void onSubmit(Form<?> form, AjaxRequestTarget target);

	/**
	 * Callback method called after an ajax event has raised an exception 
	 * @param component
	 * @param target
	 */
	public void onError(Form<?> form, AjaxRequestTarget target);

}

package ar.edu.itba.it.dev.common.web.components.events;

import org.apache.wicket.markup.html.form.Form;


/**
 * Inteface that provides a listener for form submit events
 * @author Pablo Abad (pabad@itba.edu.ar)
 */
public interface SubmitEventListener extends EventListener {
	/**
	 * Callback method called on succesfull form submission
	 * @param form Form being processed
	 */
	public void onSubmit(Form<?> form);

	/**
	 * Callback method called on invalid form submission. Gets called when 
	 * validation, conversion o model update operations fail
	 * @param form Form being processed
	 */
	public void onError(Form<?> form);
}

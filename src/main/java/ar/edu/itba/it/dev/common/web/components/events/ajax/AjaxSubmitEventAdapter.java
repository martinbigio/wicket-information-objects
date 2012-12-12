package ar.edu.itba.it.dev.common.web.components.events.ajax;

import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;

import ar.edu.itba.it.dev.common.web.components.events.SubmitEventListener;

/**
 * Submit event listener that adapts submit request to handle ajax submits
 * <p>
 * This class is expected to be used on forms when there is a need to handle Ajax
 * submissions. It will process the form submission and derive to ajax methods 
 * </p>
 * <p>
 * Note that by default, if the submission is non asynchronous an error will be 
 * raised.
 * </p>
 * @author Pablo Abad (pabad@itba.edu.ar)
 */
public abstract class AjaxSubmitEventAdapter implements AjaxSubmitEventListener, SubmitEventListener {
	private static final long serialVersionUID = 1L;
	private boolean acceptNormalSubmits;
	
	public AjaxSubmitEventAdapter() {
		super();
		acceptNormalSubmits = false;
	}
	
	public AjaxSubmitEventAdapter(boolean acceptNormalSubmits) {
		super();
		this.acceptNormalSubmits = acceptNormalSubmits;
	}

	
	private AjaxRequestTarget getAjaxTarget() {
		AjaxRequestTarget target = AjaxRequestTarget.get();
		if (target == null && !acceptNormalSubmits) {
			throw new WicketRuntimeException("This form can't process non ajax submits");
		}
		return target;
	}
	
	@Override
	public final void onError(Form<?> form) {
		AjaxRequestTarget target = getAjaxTarget();
		onError(form, target);
	}

	@Override
	public final void onSubmit(Form<?> form) {
		AjaxRequestTarget target = getAjaxTarget();
		onSubmit(form, target);
	}
}

package ar.edu.itba.it.dev.common.web.actions;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.IModel;

public interface IAJAXAction<T> extends IAction<T> {

	public void onExecute(Component component, IModel<T> data, AjaxRequestTarget target);
}

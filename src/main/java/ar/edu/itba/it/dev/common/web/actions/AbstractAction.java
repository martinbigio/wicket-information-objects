/*
 * Copyright (c) 2009 IT - ITBA -- All rights reserved
 */
package ar.edu.itba.it.dev.common.web.actions;

import org.apache.wicket.Component;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.resource.ResourceReference;

public abstract class AbstractAction<T> implements ICommonAction<T> {
	private static final long serialVersionUID = 1L;
	
	private IModel<String> captionModel;
	
	public AbstractAction(IModel<String> captionModel) {
		super();
		this.captionModel = captionModel;
	}

	@Override
	public IModel<String> getCaption(IModel<T> data) {
		return this.captionModel;
	}

	@Override
	public IModel<String> getDescription(IModel<T> data) {
		return null;
	}
	
	@Override
	public IModel<String> getConfirmationMessage(IModel<T> data) {
		return null;
	}

	@Override
	public ResourceReference getImage(IModel<T> data) {
		return null;
	}

	@Override
	public boolean isVisible(IModel<T> data) {
		return true;
	}
	
	@Override
	public boolean canExecute() {
		return true;
	}

	@Override
	public abstract void onExecute(Component component, IModel<T> data);
}

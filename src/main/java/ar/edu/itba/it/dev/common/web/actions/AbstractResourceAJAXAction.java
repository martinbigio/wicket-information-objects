/*
 * Copyright (c) 2009 IT - ITBA -- All rights reserved
 */
package ar.edu.itba.it.dev.common.web.actions;

import org.apache.wicket.request.resource.ResourceReference;

public abstract class AbstractResourceAJAXAction<T> extends AbstractResourceAction<T> implements IAJAXAction<T> {

	public AbstractResourceAJAXAction(String actionKey) {
		super(actionKey);
	}

	public AbstractResourceAJAXAction(String actionKey, ResourceReference img) {
		super(actionKey, img);
	}

	public AbstractResourceAJAXAction(String captionKey, String descriptionKey, ResourceReference img) {
		super(captionKey, descriptionKey, img);
	}

	public AbstractResourceAJAXAction(String captionKey, ResourceReference img, String confirmationMessageKey) {
		super(captionKey, img, confirmationMessageKey);
	}
	
	public AbstractResourceAJAXAction(String captionKey, String descriptionKey, ResourceReference img, String confirmationMessageKey) {
		super(captionKey, descriptionKey, img, confirmationMessageKey);
	}
}

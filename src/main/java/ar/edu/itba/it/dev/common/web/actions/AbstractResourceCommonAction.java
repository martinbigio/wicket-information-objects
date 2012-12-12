/*
 * Copyright (c) 2009 IT - ITBA -- All rights reserved
 */
package ar.edu.itba.it.dev.common.web.actions;

import org.apache.wicket.request.resource.ResourceReference;


public abstract class AbstractResourceCommonAction<T> extends AbstractResourceAction<T> implements ICommonAction<T> {
	
	public AbstractResourceCommonAction(String actionKey) {
		super(actionKey);
	}

	public AbstractResourceCommonAction(String actionKey, ResourceReference img) {
		super(actionKey, img);
	}
	
	public AbstractResourceCommonAction(String captionKey, String descriptionKey, ResourceReference img) {
		super(captionKey, descriptionKey, img);
	}

	public AbstractResourceCommonAction(String captionKey, ResourceReference img, String confirmationMessageKey) {
		super(captionKey, img, confirmationMessageKey);
	}
	
	public AbstractResourceCommonAction(String captionKey, String descriptionKey, ResourceReference img, String confirmationMessageKey) {
		super(captionKey, descriptionKey, img, confirmationMessageKey);
	}
}

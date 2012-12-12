/*
 * Copyright (c) 2009 IT - ITBA -- All rights reserved
 */
package ar.edu.itba.it.dev.common.web.actions;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.resource.ResourceReference;

import com.google.common.base.Preconditions;


/**
 * Base class to be used for simple actions that display messages that can be localized
 * It may be extended to determine the actual action when seelcted
 * <p>
 * Note that the cinstance itself is is passed when resolving the localization, allowing 
 * child classes to define javabean attributesd that may make part of the message 
 * </p>
 */
public abstract class AbstractResourceAction<T> implements IAction<T> {
	
	private static final String CAPTION_SUFFIX = ".caption";
	private static final String DESCRIPTION_SUFFIX = ".description";
	
	private String captionKey;
	private String descriptionKey;
	private ResourceReference image;
	private String confirmationMessageKey;
	

	public AbstractResourceAction(String actionKey) {
		Preconditions.checkNotNull(actionKey);
		init(actionKey + CAPTION_SUFFIX, actionKey + DESCRIPTION_SUFFIX, null);
	}

	public AbstractResourceAction(String actionKey, ResourceReference img) {
		Preconditions.checkNotNull(actionKey);
		init(actionKey + CAPTION_SUFFIX, actionKey + DESCRIPTION_SUFFIX, img);
	}
	
	public AbstractResourceAction(String captionKey, String descriptionKey, ResourceReference img) {
		Preconditions.checkNotNull(captionKey);
		init(captionKey, descriptionKey, img);
	}

	public AbstractResourceAction(String captionKey, ResourceReference img, String confirmationMessageKey) {
		this(captionKey, img);
		this.confirmationMessageKey = confirmationMessageKey;
	}
	
	public AbstractResourceAction(String captionKey, String descriptionKey, ResourceReference img, String confirmationMessageKey) {
		this(captionKey, descriptionKey, img);
		this.confirmationMessageKey = confirmationMessageKey;
	}
	
	private void init(String captionKey, String descriptionKey, ResourceReference img) {
		this.captionKey = captionKey;
		this.descriptionKey = descriptionKey;
		this.image = img;
	}
	
	@Override
	public IModel<String> getCaption(IModel<T> model) {
		return new StringResourceModel(captionKey, new Model<AbstractResourceAction<T>>(this));
	}
	@Override
	public IModel<String> getDescription(IModel<T> model) {
		if (descriptionKey != null) {
			return new StringResourceModel(descriptionKey, new Model<AbstractResourceAction<T>>(this));
		}
		return null;
	}
	
	@Override
	public IModel<String> getConfirmationMessage(IModel<T> data) {
		return confirmationMessageKey == null ? null : new StringResourceModel(confirmationMessageKey, data);
	}
	
	@Override
	public ResourceReference getImage(IModel<T> model) {
		return image;
	}
	
	@Override
	public boolean isVisible(IModel<T> data) {
		return true;
	}
	
	
	@Override
	public boolean canExecute() {
		return true;
	}
	
}

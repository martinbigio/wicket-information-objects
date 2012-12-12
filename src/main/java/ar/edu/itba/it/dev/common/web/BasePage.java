/*
 * Copyright (c) 2008 IT - ITBA -- All rights reserved
 */
package ar.edu.itba.it.dev.common.web;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.event.IEvent;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.IModel;

public abstract class BasePage<T> extends WebPage {

	private FeedbackPanel feedbackPanel;

	public BasePage(IModel<T> model) {
		super(model);
	}

	public BasePage() {
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();

		feedbackPanel = new FeedbackPanel("feedbackPanel") {
			@Override
			public void onEvent(IEvent<?> event) {
				super.onEvent(event);
				if (event.getPayload() instanceof AjaxRequestTarget) {
					((AjaxRequestTarget) event.getPayload()).add(this);
				}
			}
		};
		feedbackPanel.setOutputMarkupId(true);
		add(feedbackPanel);

	}

	protected boolean showMenu() {
		return true;
	}

	@SuppressWarnings("unchecked")
	protected IModel<T> getModel() {
		return (IModel<T>) getDefaultModel();
	}

	@SuppressWarnings("unchecked")
	protected T getModelObject() {
		return (T) getDefaultModelObject();
	}

	protected void setModel(IModel<? extends T> model) {
		setDefaultModel(model);
	}

	@Override
	protected void configureResponse(
			org.apache.wicket.request.http.WebResponse response) {
		super.configureResponse(response);
		response.setHeader("Cache-Control",	"no-cache, max-age=0, must-revalidate, no-store");
	}
}

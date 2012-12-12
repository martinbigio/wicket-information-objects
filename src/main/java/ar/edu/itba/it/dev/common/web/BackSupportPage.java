/*
 * Copyright (c) 2009 IT - ITBA -- All rights reserved
 */
package ar.edu.itba.it.dev.common.web;

import org.apache.wicket.Page;
import org.apache.wicket.model.IModel;

/**
 * Web page that receives the previous page in the constructor
 * and has a method to return to that instance  
 */
public class BackSupportPage<T> extends BasePage<T> {

	private Page previousPage;
	
	public BackSupportPage(Page previousPage) {
		this.previousPage = previousPage;
	}
	
	public BackSupportPage(Page previousPage, IModel<T> model) {
		super(model);
		this.previousPage = previousPage;
	}
	
	public void returnToPreviousPage() {
		setResponsePage(previousPage);
	}
	
	public Page getReturnPage() {
		return previousPage;
	}
}

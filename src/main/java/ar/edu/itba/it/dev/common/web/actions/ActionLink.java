/*
 * Copyright (c) 2009 IT - ITBA -- All rights reserved
 */
package ar.edu.itba.it.dev.common.web.actions;

import org.apache.wicket.markup.html.link.Link;

/**
 * Link that executes an action
 */
public class ActionLink<T> extends Link<T> {
	private static final long serialVersionUID = 1L;
	
	private ICommonAction<T> action;

	public ActionLink(String id, ICommonAction<T> action) {
		super(id);
		this.action = action;
	}
	
	
	@Override
	public void onClick() {
		action.onExecute(this, null);
	}

}

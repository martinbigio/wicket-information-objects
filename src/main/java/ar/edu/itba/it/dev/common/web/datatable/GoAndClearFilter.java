/*
 * Copyright (c) 2008 IT - ITBA -- All rights reserved
 */ 
package ar.edu.itba.it.dev.common.web.datatable;

import org.apache.wicket.Component;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.FilterForm;
import org.apache.wicket.markup.html.form.AbstractSubmitLink;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.request.resource.PackageResourceReference;
import org.apache.wicket.request.resource.ResourceReference;
import org.apache.wicket.util.lang.WicketObjects;

import ar.edu.itba.it.dev.common.web.components.Wicket;
import ar.edu.itba.it.dev.common.web.components.events.ClickableEventAdapter;

public class GoAndClearFilter extends Panel {

	public static final ResourceReference GO_ICON = new PackageResourceReference(GoAndClearFilter.class, "filter.gif");
	public static final ResourceReference CLEAR_ICON = new PackageResourceReference(GoAndClearFilter.class, "clear.gif");
	
	private Object originalState;
	private FilterForm<?> form; 
	
	public GoAndClearFilter(String id, final FilterForm<?> form) {
		super(id);

		this.form = form;
		add(Wicket.link("clear", CLEAR_ICON, new ClickableEventAdapter() {
			@Override
			public void onClick(Component component) {
				form.clearInput();
				form.setDefaultModelObject(WicketObjects.cloneModel(originalState));
				onClearSubmit();
			}
		}));
		
		AbstractSubmitLink goLink = Wicket.submitLink("go", GO_ICON, new ClickableEventAdapter() {
			@Override
			public void onClick(Component component) {
				onGoSubmit();
			}
		});
		add(goLink);
		
		form.setDefaultButton(goLink);
	}
	
	@Override
	protected void onBeforeRender() {
		if (originalState == null ){
			originalState = WicketObjects.cloneModel(form.getModelObject());
		}
		super.onBeforeRender();
	}
	
	protected void onClearSubmit() {
		// default action is do nothing
	}

	protected void onGoSubmit() {
		// default action is do nothing
	}
}

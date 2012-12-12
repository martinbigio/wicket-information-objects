/*
 * Copyright (c) 2009 IT - ITBA -- All rights reserved
 */
package ar.edu.itba.it.dev.common.web.datatable;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.FilterForm;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.FilteredAbstractColumn;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;

import ar.edu.itba.it.dev.common.web.actions.IAction;

public class ActionColumn<T> extends FilteredAbstractColumn<T> {

	private static final String ACTION_TITLE_KEY = "table.actions";
	private static final String ACTION_TITLE_CAPTION = "Actions";

	protected List<IAction<T>> actions = new ArrayList<IAction<T>>();

	public ActionColumn() {
		super(new ResourceModel(ACTION_TITLE_KEY, ACTION_TITLE_CAPTION));
	}

	public ActionColumn<T> addAction(IAction<T> action) {
		this.actions.add(action);
		return this;
	}

	@Override
	public Component getFilter(String componentId, FilterForm<?> form) {
		return new GoAndClearFilter(componentId, form);
	}
	
	@Override
	public void populateItem(Item<ICellPopulator<T>> cellItem, String componentId, IModel<T> rowModel) {
		cellItem.add(new ActionCellPanel<T>(componentId, rowModel, actions));
		cellItem.add(AttributeModifier.replace("align", "center"));
	}

	public boolean hasActions() {
		return !actions.isEmpty();
	}
}
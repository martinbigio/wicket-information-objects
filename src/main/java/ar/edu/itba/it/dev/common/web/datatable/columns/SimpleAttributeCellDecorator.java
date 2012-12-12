package ar.edu.itba.it.dev.common.web.datatable.columns;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;

/**
 * Cell decorator that contributes a SimpleAttribute to the cell
 */
public abstract class SimpleAttributeCellDecorator<T> implements CellDecorator<T> {
	@Override
	public void decorate(Item<ICellPopulator<T>> item, IModel<T> rowModel) {
		String attribute = getAttribute(rowModel);
		String css = getValue(rowModel);
		if (css != null) {
			item.add(AttributeModifier.replace(attribute, css));
		}
	}
	
	public abstract String getValue(IModel<T> rowModel);
	public abstract String getAttribute(IModel<T> rowModel);
	
}

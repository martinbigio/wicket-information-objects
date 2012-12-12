package ar.edu.itba.it.dev.common.web.datatable.columns;

import java.io.Serializable;

import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;


/**
 * Cell decorator. Implementations of this interface can contirbute behaviours to a cell, 
 * or even modify it's value
 */
public interface CellDecorator<T> extends Serializable {
	/**
	 * Subclasses must implement this method to 
	 * decorate the cell item
	 */
	public void decorate(Item<ICellPopulator<T>> item, IModel<T> rowModel);
}

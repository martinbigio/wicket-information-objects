package ar.edu.itba.it.dev.common.web.datatable.columns;

import java.util.List;

import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.FilteredPropertyColumn;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.util.convert.IConverter;

import ar.edu.itba.it.dev.common.web.datatable.IConvertableColumn;

/**
 * Abstract column class that allows custom filter property as well as styling via css classes
 * @param <T>
 */
public abstract class StyledFilterColumn<T> extends FilteredPropertyColumn<T> implements IConvertableColumn<T>{
	
	private String filterProperty;
	private String css;
	private IConverter customConverter; 
	private List<CellDecorator<T>> cellDecorators;

	@Override
	public String getCssClass() {
		return this.css;
	}

	public StyledFilterColumn(ColumnBuilder<T> builder) {
		super(new ResourceModel(builder.getTitleKey()), builder.getSortProperty(), builder.getProperty());
		this.css = builder.getCss();
		this.filterProperty = builder.getFilterProperty();
		this.customConverter = builder.getCustomConverter();
		this.cellDecorators = builder.getCellDecorators();
	}
	
	protected String getFilterProperty() {
		return filterProperty;
	}
	@Override
	protected IModel<?> createLabelModel(IModel<T> rowModel) {
		if (customConverter == null) {
			return super.createLabelModel(rowModel);
		}
		else {
			return new ConverterModel(customConverter, super.createLabelModel(rowModel));
		}
	}
	
	@Override
	public void populateItem(Item<ICellPopulator<T>> item, String componentId,
			IModel<T> rowModel) {
		super.populateItem(item, componentId, rowModel);
		if(!cellDecorators.isEmpty()) {
			for(CellDecorator<T> c : cellDecorators) {
				c.decorate(item, rowModel);
			}
		}
	}
	
	@Override
	public IConverter getConverter() {
		return customConverter;
	}
}

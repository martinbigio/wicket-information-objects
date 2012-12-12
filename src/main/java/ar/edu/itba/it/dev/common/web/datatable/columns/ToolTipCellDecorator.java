package ar.edu.itba.it.dev.common.web.datatable.columns;

import org.apache.wicket.model.IModel;

/**
 * Adds a tooltip to the cell
 */
public abstract class ToolTipCellDecorator<T> extends SimpleAttributeCellDecorator<T> {
	
	private static String ATTRIBUTE = "title";
	
	@Override
	public String getAttribute(IModel<T> rowModel) {
		return ATTRIBUTE;
	}
	
	/**
	 * Factory method that provides constant tooltip to be added to the cell
	 * @param <T>
	 * @param css
	 * @return
	 */
	public static <T> ToolTipCellDecorator<T> constant(Class<T> type, final String tooltip) {
		return new ToolTipCellDecorator<T>() {
			@Override
			public String getValue(IModel<T> rowModel) {
				return tooltip;
			}
		};
	}
}

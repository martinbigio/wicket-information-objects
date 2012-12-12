package ar.edu.itba.it.dev.common.web.datatable.columns;

import org.apache.wicket.model.IModel;

/**
 * Cell decorator that contributes a style to the cell
 */
public abstract class StyleCellDecorator<T> extends SimpleAttributeCellDecorator<T> {
	private static final String ATTRIBUTE = "class";
	@Override
	public String getAttribute(IModel<T> rowModel) {
		return ATTRIBUTE;
	}

	/**
	 * Factory method that provides constant style decorator that adds a fixed css class to a cell
	 * @param <T>
	 * @param css
	 * @return
	 */
	public static <T> StyleCellDecorator<T> constant(Class<T> type, final String css) {
		return new StyleCellDecorator<T>() {
			@Override
			public String getValue(IModel<T> rowModel) {
				return css;
			}

		};
	}
}

package ar.edu.itba.it.dev.common.web.datatable;

import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.util.convert.IConverter;

public interface IConvertableColumn<T> extends IColumn<T>{

	public IConverter getConverter();
}

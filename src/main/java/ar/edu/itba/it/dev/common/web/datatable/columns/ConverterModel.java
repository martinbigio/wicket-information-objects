package ar.edu.itba.it.dev.common.web.datatable.columns;


import org.apache.wicket.Session;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.convert.IConverter;

import com.google.common.base.Preconditions;

public class ConverterModel extends AbstractReadOnlyModel<String> {
	private IConverter converter;
	private IModel<?> delegate;
	
	public ConverterModel(IConverter converter, IModel<?> delegate) {
		super();
		Preconditions.checkNotNull(converter);
		Preconditions.checkNotNull(delegate);
		this.converter = converter;
		this.delegate = delegate;
	}

	@Override
	public String getObject() {
		Object temp = delegate.getObject();
		return converter.convertToString(temp, Session.get().getLocale());
	}

	@Override
	public void detach() {
		delegate.detach();
	}
}

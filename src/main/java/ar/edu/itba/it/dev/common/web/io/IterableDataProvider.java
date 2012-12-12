package ar.edu.itba.it.dev.common.web.io;

import java.util.Iterator;
import java.util.List;

import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;

import ar.edu.itba.it.dev.common.jpa.domain.Identifiable;
import ar.edu.itba.it.dev.common.web.Models;

import com.google.common.collect.Iterables;

public class IterableDataProvider<T> implements IDataProvider<T> {

	private IModel<? extends Iterable<T>> iterable;
	
	public IterableDataProvider(IModel<? extends Iterable<T>> iterable) {
		this.iterable = iterable;
	}

	@Override
	public void detach() {
		iterable.detach();
	}

	@Override
	public Iterator<? extends T> iterator(int first, int count) {
		return target().iterator();
	}

	@Override
	public int size() {
		return Iterables.size(target());
	}

	@SuppressWarnings("unchecked")
	@Override
	public IModel<T> model(Object object) {
		if (object instanceof Identifiable) {
			Identifiable ident = (Identifiable) object;
			return (IModel<T>) Models.newEntityModel(ident.getClass(), ident.getId());
		} else {
			// resolve model object using list index
			if (target() instanceof List) {
				return new ListItemModel<T>((IModel<List<T>>) iterable, ((List<T>)target()).indexOf(object));
			} else {
				throw new IllegalStateException();
			}
		}
	}
	
	private Iterable<T> target() {
		return iterable.getObject(); 
	}
	
	private static class ListItemModel<T> extends AbstractReadOnlyModel<T> {

		IModel<? extends List<T>> list;
		int index;
		
		ListItemModel(IModel<? extends List<T>> list, int index) {
			this.list = list;
			this.index = index;
		}

		@Override
		public T getObject() {
			return list.getObject().get(index);
		}
	}
}

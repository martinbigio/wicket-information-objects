package ar.edu.itba.it.dev.common.web;

import java.io.Serializable;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.markup.repeater.util.ModelIteratorAdapter;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import ar.edu.itba.it.dev.common.io.ObjectField;
import ar.edu.itba.it.dev.common.jpa.domain.APIObject;
import ar.edu.itba.it.dev.common.jpa.domain.IO;
import ar.edu.itba.it.dev.common.jpa.domain.Identifiable;

import com.google.common.collect.Lists;

/**
 * Base implementation of the model factory
 * @author Pablo Abad (pabad@itba.edu.ar)
 */
public class Models {
	
	@SuppressWarnings("rawtypes")
	public static IModel<?> forAttribute(IModel<?> base, ObjectField attribute) {
		return new PropertyModel(base, attribute.getFieldName());
	}
	
	@SuppressWarnings("unchecked")
	public static <T extends APIObject> IModel<T> newAPIModel(final Class<T> clazz, IO io) {
		final IModel<IO> ioModel = new EntityResolverModel<IO>((Class<IO>) io.getRealClass(), io);
		
		return new LoadableDetachableModel<T>() {
			@Override
			protected T load() {
				return IO.build(clazz, ioModel.getObject());
			}
		};
	}
	
	public static <T> IModel<T> newDiscardableModel(T obj) {
		return new LoadableDetachableModel<T>(obj) {
			@Override
			protected T load() {
				throw new UnsupportedOperationException("Can't reload a discardable model!");
			}
		};
	}

	public static <T extends Identifiable> IModel<T> newEntityModel(Class<T> type, T o) {
		return new CompoundPropertyModel<T>(new EntityResolverModel<T>(type, o));
	}

	public static <T extends Identifiable> IModel<T> newEntityModel(Class<T> type,  Integer id) {
		return new CompoundPropertyModel<T>(new EntityResolverModel<T>(type, id));
	}

	public static <T extends Identifiable> IModel<T> newEmptyEntityModel(Class<T> type) {
		return new CompoundPropertyModel<T>(new EntityResolverModel<T>(type, (T)null));
	}
	
	public static <T extends Identifiable> Iterator<IModel<T>> newEntityModelIterator(final Class<T> type, Iterable<T> values) {
		return newEntityModelIterator(type, values.iterator());
	}
	
	public static <T extends Identifiable> Iterator<IModel<T>> newEntityModelIterator(final Class<T> type, Iterator<T> values) {
		return new ModelIteratorAdapter<T>(values) {
			@Override
			protected IModel<T> model(T object) {
				return Models.newEntityModel(type, object);
			}
		};
	}
	
	public static <T extends Serializable> Iterator<IModel<T>> newModelIterator(final Class<T> type, Iterator<T> values) {
		return new ModelIteratorAdapter<T>(values) {
			@Override
			protected IModel<T> model(T object) {
				return new Model<T>(object);
			}
		};
	}
	
	public static <T extends Serializable> Model<T> newModel(T obj) {
		return new Model<T>(obj);
	}
	
	public static <T> IModel<T> newPropertyModel(IModel<?> base, String property) {
		return new PropertyModel<T>(base, property);
	}
	
	public static <T extends Enum<T>> IModel<List<T>> newEnumListModel(final Class<T> type) {
		return new LoadableDetachableModel<List<T>>() {
			@Override
			protected List<T> load() {
				return Lists.newArrayList(EnumSet.allOf(type));
			}
		};
	}
}

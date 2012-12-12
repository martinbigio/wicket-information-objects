package ar.edu.itba.it.dev.common.web.io;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.wicket.injection.Injector;
import org.apache.wicket.model.IModel;

import ar.edu.itba.it.dev.common.io.CollectionObjectField;
import ar.edu.itba.it.dev.common.io.CompoundObjectField;
import ar.edu.itba.it.dev.common.io.InlineObjectField;
import ar.edu.itba.it.dev.common.io.ObjectDefinition;
import ar.edu.itba.it.dev.common.io.ObjectField;
import ar.edu.itba.it.dev.common.io.ObjectField.Type;
import ar.edu.itba.it.dev.common.jpa.domain.Identifiable;
import ar.edu.itba.it.dev.common.jpa.domain.ReflectionUtils;
import ar.edu.itba.it.dev.common.jpa.filter.EntityStore;

import com.google.common.base.Preconditions;
import com.google.inject.Inject;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import flexjson.ObjectBinder;
import flexjson.ObjectFactory;
import flexjson.TypeContext;
import flexjson.transformer.AbstractTransformer;

@SuppressWarnings({"unchecked", "rawtypes"})
public class JsonSerializableModel<T> implements IModel<T> {

	private transient boolean attached = false;
	private transient T transientModelObject;
	private Class<?> clazz;
	private String json;
	private final IModel<ObjectDefinition> viewer;

	public JsonSerializableModel(Class<T> clazz, T object, IModel<ObjectDefinition> viewer) {
		setObject(object);
		this.clazz = Preconditions.checkNotNull(clazz);
		this.viewer = Preconditions.checkNotNull(viewer);
	}
	
	public JsonSerializableModel(Class<T> clazz, IModel<ObjectDefinition> viewer) {
		setObject(ReflectionUtils.newInstance(Preconditions.checkNotNull(clazz)));
		this.clazz = clazz;
		this.viewer = Preconditions.checkNotNull(viewer);
	}

	@Override
	public void detach() {
		if (attached) {
			try {
				onDetach();
			} finally {
				attached = false;
				transientModelObject = null;
			}
		}
	}

	@Override
	public T getObject() {
		if (!attached) {
			attached = true;
			transientModelObject = load();
		}
		return transientModelObject;
	}

	private T load() {
		JSONDeserializer<T> deser = new JSONDeserializer();
		processDeserialize(deser, "", viewer().getFields());
		T obj = deser.deserialize(json, clazz);
		return obj;
	}

	@Override
	public void setObject(final T object) {
		attached = true;
		transientModelObject = Preconditions.checkNotNull(object);
	}

	private void onDetach() {
		JSONSerializer ser = new JSONSerializer();
		ser.prettyPrint(true);
		process(ser, "", viewer().getFields());
		ser.exclude("*");
		
		json = ser.deepSerialize(transientModelObject);
		viewer.detach();
	}
	
	private void process(JSONSerializer ser, String prefix, List<ObjectField> fields) {
		for (ObjectField a : fields) {
			String property = append(prefix, a.getFieldName());
			if (a instanceof CompoundObjectField) {
				process(ser, property, ((CompoundObjectField)a).getFields());
			} else if (a instanceof CollectionObjectField) {
				CollectionObjectField collectionAttribute = (CollectionObjectField)a;
				if (collectionAttribute.getFields().size() == 1 && collectionAttribute.getFields().get(0) instanceof InlineObjectField) {
					ObjectField field = collectionAttribute.getFields().get(0);
					if (!Serializable.class.isAssignableFrom(field.getClazz())) {
						ser.transform(IterableOfEntityRefTransformer.INSTANCE, property);
					}
				} else {
					process(ser, property, ((CollectionObjectField)a).getFields());
				}
			} else {
				if (a.getType() == Type.ENTITY) {
					ser.transform(EntityRefTransformer.INSTANCE, property);
				}	
			}
			ser.include(property);
		}
	}
	
	private <S> void processDeserialize(JSONDeserializer<S> deser, String prefix, List<ObjectField> attributes) {
		for (ObjectField a : attributes) {
			String property = append(prefix, a.getFieldName());
			if (a instanceof CompoundObjectField) {
				deser.use(ObjectObjectFactory.INSTANCE, property);
			} else if (a instanceof CollectionObjectField) {
				CollectionObjectField collectionAttribute = (CollectionObjectField)a;
				if (collectionAttribute.getFields().size() == 1 && collectionAttribute.getFields().get(0) instanceof InlineObjectField) {
					deser.use(IterableOfEntityRefObjectFactory.INSTANCE, property);
				} else {
					processDeserialize(deser, property, ((CollectionObjectField)a).getFields());
				}
			} else {
				if (a.getType() == Type.ENTITY) {
					deser.use(EntityRefObjectFactory.INSTANCE, property);
				}
			}
		}
	}
	
	private String append(String prefix, String property) {
		if (!prefix.equals("") && !prefix.endsWith(".") && !property.equals("")) {
			prefix += ".";
		}
		return prefix + property;
	}
	
	private ObjectDefinition viewer() {
		return viewer.getObject();
	}
	
	private static class EntityRefTransformer extends AbstractTransformer {
		
		public static EntityRefTransformer INSTANCE = new EntityRefTransformer();
		
		private EntityRefTransformer() { }

		@Override
		public void transform(Object obj) {
			Identifiable identifiable = (Identifiable) obj;
			getContext().writeOpenObject();
			getContext().writeName("class");
			getContext().writeQuoted(obj.getClass().getName());
			getContext().writeComma();
			getContext().writeName("id");
			getContext().write(String.valueOf(identifiable.getId()));
			getContext().writeCloseObject();
		}
	}
	
	private static class IterableOfEntityRefTransformer extends AbstractTransformer {
		
		public static IterableOfEntityRefTransformer INSTANCE = new IterableOfEntityRefTransformer();
		
		private IterableOfEntityRefTransformer() { }

		@Override
		public void transform(Object obj) {
	        Iterable iterable = (Iterable) obj;
	        TypeContext typeContext = getContext().writeOpenArray();
	        for (Object item : iterable) {
	            if (!typeContext.isFirst()) getContext().writeComma();
	            typeContext.setFirst(false);
	            EntityRefTransformer.INSTANCE.transform(item);
	        }
	        getContext().writeCloseArray();
		}
	}

	
	private static class EntityRefObjectFactory implements ObjectFactory {
		
		public static EntityRefObjectFactory INSTANCE = new EntityRefObjectFactory();
		
		@Inject private EntityStore entities;
		
		private EntityRefObjectFactory() {
			Injector.get().inject(this);
		}

		@Override
		public Object instantiate(ObjectBinder context, Object value, java.lang.reflect.Type targetType, Class targetClass) {
			Class<? extends Identifiable> clazz;
			try {
				clazz = (Class<? extends Identifiable>) Class.forName((String) ((Map<String, Object>)value).get("class"));
				return entities.fetch(clazz, (Integer) ((Map<String, Object>)value).get("id"));
			} catch (ClassNotFoundException e) {
				throw new RuntimeException(e);
			}
		}
	}
	
	private static class IterableOfEntityRefObjectFactory implements ObjectFactory {
		
		public static IterableOfEntityRefObjectFactory INSTANCE = new IterableOfEntityRefObjectFactory();
		
		private IterableOfEntityRefObjectFactory() {
			Injector.get().inject(this);
		}

		@Override
		public Object instantiate(ObjectBinder context, Object value, java.lang.reflect.Type targetType, Class targetClass) {
			Iterable iter = (Iterable) value;
			Collection result;
			if (((Class) ((ParameterizedType)targetType).getRawType()).equals(Set.class)) {
				result = new HashSet();
			} else if (((Class) ((ParameterizedType)targetType).getRawType()).equals(List.class)) {
				result = new LinkedList();
			} else {
				throw new UnsupportedOperationException();
			}
			
	        ParameterizedType parametrizedType = (ParameterizedType) targetType;
	        Class<?> genericClazz = (Class<?>) parametrizedType.getActualTypeArguments()[0];
			
			for (Object obj: iter) {
				result.add(EntityRefObjectFactory.INSTANCE.instantiate(context, obj, genericClazz, genericClazz));
			}
			return result;
		}
	}

	private static class ObjectObjectFactory implements ObjectFactory {
		
		public static ObjectObjectFactory INSTANCE = new ObjectObjectFactory();
		private ObjectObjectFactory() { }
		
		@Override
		public Object instantiate(ObjectBinder binder, Object obj, java.lang.reflect.Type type, Class clazz) {
			Object result = ReflectionUtils.newInstance((Class)type);
			result = binder.bind(obj, result);
			return result;
		}
	}
}
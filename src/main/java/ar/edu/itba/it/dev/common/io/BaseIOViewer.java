package ar.edu.itba.it.dev.common.io;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import ar.edu.itba.it.dev.common.io.annotations.Narrowed;

import com.google.common.base.Preconditions;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;

// TODO: introspect inherited fields
public class BaseIOViewer implements ObjectDefinition {

	private final Class<?> clazz;
	private final Multimap<Class<?>, Class<? extends Annotation>> overrides;

	public BaseIOViewer(CompoundObjectField attribute) {
		this.clazz = attribute.getClazz();
		this.overrides = HashMultimap.create();
	}
	
	public BaseIOViewer(Class<?> clazz, Multimap<Class<?>, Class<? extends Annotation>> overrides) {
		this.clazz = Preconditions.checkNotNull(clazz);
		this.overrides = Preconditions.checkNotNull(overrides);
	}

	@Override
	public List<ObjectField> getFields() {
		return get(clazz, null);
	}

	@SuppressWarnings("unchecked")
	private List<ObjectField> get(Class<?> clazz, Class<? extends Annotation> annot) {
		List<ObjectField> result = Lists.newLinkedList();
		for (Field field : clazz.getFields()) {
			if (!Modifier.isStatic(field.getModifiers()) && (annot == null || field.getAnnotation(annot) != null)) {
				try {
					if (hasAnyOf(field, Entity.class, Embeddable.class) && !field.isAnnotationPresent(Narrowed.class) &&
							!overrides.containsEntry(field.getType(), Narrowed.class)) {
						result.add(new CompoundObjectField(field.getName(), field.getType(), Lists.newArrayList(field.getAnnotations()), get(field.getType(), annot)));
					} else if (hasAnyOf(field, ElementCollection.class, OneToMany.class, ManyToMany.class)) {
				        ParameterizedType parametrizedType = (ParameterizedType) field.getGenericType();
				        Class<?> genericClazz = (Class<?>) parametrizedType.getActualTypeArguments()[0];
						
				        List<ObjectField> attributes = Lists.newArrayList();
				        if (field.isAnnotationPresent(Narrowed.class) || Serializable.class.isAssignableFrom(genericClazz)) {
				        	attributes.add(new InlineObjectField(field.getName(), "", genericClazz, Lists.newArrayList(field.getAnnotations())));
				        } else {
							attributes.addAll(get(genericClazz, annot));
							if (attributes.isEmpty()) {
								throw new RuntimeException("TODO");
	//							attributes.add(new SimpleAttribute(field.getName(), "", field.getType(), Lists.newArrayList(field.getAnnotations())));
							}
				        }
						result.add(new CollectionObjectField(field.getName(), field.getType(), Lists.newArrayList(field.getAnnotations()), genericClazz, attributes));
					} else if (Serializable.class.isAssignableFrom(field.getType()) ||
							(hasAnyOf(field, Entity.class, Embeddable.class) &&	(field.isAnnotationPresent(Narrowed.class) || overrides.containsEntry(field.getType(), Narrowed.class)))) {
						result.add(new InlineObjectField(field.getName(), field.getType(), Lists.newArrayList(field.getAnnotations())));
					} else {
						throw new RuntimeException("Field must be an entity, embeddable or serializable");
					}
				} catch (Throwable t) {
					throw new RuntimeException(t);
				}
			}
		}
		return result;
	}
	
	public boolean hasAnyOf(Field field, Class<? extends Annotation>... annotatations) {
		for (Class<? extends Annotation> annotation: annotatations) {
			if (field.isAnnotationPresent(annotation) || field.getType().isAnnotationPresent(annotation)) {
				return true;
			}
		}
		return false;
	}
}
package ar.edu.itba.it.dev.common.io;

import java.lang.annotation.Annotation;

import javax.persistence.Embeddable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.google.common.base.Optional;

import ar.edu.itba.it.dev.common.io.annotations.Hidden;

public abstract class AbstractObjectField implements ObjectField {

	private final String key;
	private final String fieldName;
	private final Class<?> clazz;
	private final Iterable<Annotation> annotations;
	
	AbstractObjectField(String fieldName, Class<?> clazz, Iterable<Annotation> annotations) {
		this(fieldName, fieldName, clazz, annotations);
	}
	
	AbstractObjectField(String key, String fieldName, Class<?> clazz, Iterable<Annotation> annotations) {
		this.key = key;
		this.fieldName = fieldName;
		this.clazz = clazz;
		this.annotations = annotations;
	}
	
	@Override
	public String getKey() {
		return key;
	}
	
	@Override
	public String getFieldName() {
		return fieldName;
	}

	@Override
	public Iterable<Annotation> getAnnotations() {
		return annotations;
	}
	
	@Override
	public Class<?> getClazz() {
		return clazz;
	}
	
	@Override
	public boolean isHidden() {
		try {
			return hasAnnotation(Hidden.class);
		} catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}
	
	@Override
	public Type getType() {
		if (getClazz().isAnnotationPresent(Embeddable.class)) {
			return Type.COMPONENT;
		}
		for (Annotation annot: getAnnotations()) {
			if (annot.annotationType().equals(OneToMany.class) || annot.annotationType().equals(ManyToMany.class)
					|| annot.annotationType().equals(ManyToOne.class) || annot.annotationType().equals(OneToOne.class)) {
				return Type.ENTITY;
			}
		}
		return Type.COMPONENT;
	}
	
	@Override
	public boolean hasAnnotation(Class<? extends Annotation> annotation) {
		return getAnnotation(annotation).isPresent();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T extends Annotation> Optional<T> getAnnotation(Class<T> clazz) {
		for (Annotation a: getAnnotations()) {
			if (a.annotationType().equals(clazz)) {
				return (Optional<T>) Optional.of(a);
			}
		}
		return Optional.absent();
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((key == null) ? 0 : key.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof AbstractObjectField))
			return false;
		AbstractObjectField other = (AbstractObjectField) obj;
		if (key == null) {
			if (other.key != null)
				return false;
		} else if (!key.equals(other.key))
			return false;
		return true;
	}
}
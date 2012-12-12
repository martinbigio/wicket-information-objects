package ar.edu.itba.it.dev.common.io;

import java.lang.annotation.Annotation;

import com.google.common.base.Optional;

public interface ObjectField {
	
	public enum Type {ENTITY, COMPONENT, BASIC_TYPE}
	
	String getKey();
	String getFieldName();
	Class<?> getClazz();
	Iterable<Annotation> getAnnotations();
	boolean hasAnnotation(Class<? extends Annotation> annotation);
	<T extends Annotation> Optional<T> getAnnotation(Class<T> clazz);
	Type getType();
	boolean isHidden();
}
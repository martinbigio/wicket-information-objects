package ar.edu.itba.it.dev.common.io;

import java.lang.annotation.Annotation;

public class InlineObjectField extends AbstractObjectField implements ObjectField {

	InlineObjectField(String fieldName, Class<?> clazz, Iterable<Annotation> annotations) {
		this(fieldName, fieldName, clazz, annotations);
	}
	
	InlineObjectField(String key, String fieldName, Class<?> clazz, Iterable<Annotation> annotations) {
		super(key, fieldName, clazz, annotations);
	}
}
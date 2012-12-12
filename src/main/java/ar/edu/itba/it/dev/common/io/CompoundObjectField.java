package ar.edu.itba.it.dev.common.io;

import java.lang.annotation.Annotation;
import java.util.List;

public class CompoundObjectField extends AbstractObjectField implements ObjectDefinition {
	
	private final List<ObjectField> fields;
	
	CompoundObjectField(String fieldName, Class<?> clazz, Iterable<Annotation> annotations, List<ObjectField> fields) {
		this(fieldName, fieldName, clazz, annotations, fields);
	}
	
	public CompoundObjectField(String key, String fieldName, Class<?> clazz, Iterable<Annotation> annotations, List<ObjectField> fields) {
		super(key, fieldName, clazz, annotations);
		this.fields = fields;
	}
	
	@Override
	public List<ObjectField> getFields() {
		return fields;
	}
}
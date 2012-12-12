package ar.edu.itba.it.dev.common.io;

import java.lang.annotation.Annotation;
import java.util.List;

public class CollectionObjectField extends AbstractObjectField implements ObjectDefinition {
	
	private final Class<?> fieldsClass;
	private final List<ObjectField> fields;
	
	CollectionObjectField(String fieldName, Class<?> clazz, Iterable<Annotation> annotations, Class<?> fieldsClass, List<ObjectField> fields) {
		this(fieldName, fieldName, clazz, annotations, fieldsClass, fields);
	}
	
	CollectionObjectField(String key, String fieldName, Class<?> clazz, Iterable<Annotation> annotations, Class<?> fieldsClass, List<ObjectField> fields) {
		super(key, fieldName, clazz, annotations);
		this.fields = fields;
		this.fieldsClass = fieldsClass; 
	}
	
	public Class<?> getFieldsClass() {
		return fieldsClass;
	}
	
	@Override
	public List<ObjectField> getFields() {
		return fields;
	}
}
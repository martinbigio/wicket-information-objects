package ar.edu.itba.it.dev.common.io;

import java.lang.annotation.Annotation;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

public class IOViewerBuilder<T> {
	
	private Class<T> clazz;
	private Multimap<Class<?>, Class<? extends Annotation>> overrides = HashMultimap.create();
	
	private IOViewerBuilder(Class<T> clazz) {
		this.clazz = clazz;
	}
	
	public static <T> IOViewerBuilder<T> forClass(Class<T> clazz) {
		return new IOViewerBuilder<T>(clazz);
	}
	
	public OverrideRule override(Class<?> type) {
		return new OverrideRule(type);
	}
	
	public ObjectDefinition build() {
		return new BaseIOViewer(clazz, overrides);
	}
	
	public class OverrideRule {

		private Class<?> clazz;

		private OverrideRule(Class<?> clazz) {
			this.clazz = clazz;
		}
		
		public IOViewerBuilder<T> with(Class<? extends Annotation> annotation) {
			overrides.put(clazz, annotation);
			return IOViewerBuilder.this;
		}
	}
}
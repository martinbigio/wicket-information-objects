package ar.edu.itba.it.dev.common.jpa.hibernate;

import java.io.Serializable;
import java.lang.reflect.Field;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;

import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;

public class HibernateEmbeddableInstantiatorInterceptor extends EmptyInterceptor {

	@Override
	public boolean onLoad(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
		boolean modified = false;
		for (int i = 0; i < state.length; i++) {
			if (state[i] == null){
				Field field;
				try {
					field = entity.getClass().getField(propertyNames[i]);
					if (field.isAnnotationPresent(Embedded.class) || field.getType().isAnnotationPresent(Embeddable.class)) {
						state[i] = field.getType().getConstructor().newInstance();
						modified = true;
					}
				} catch (Throwable t) {
					throw new RuntimeException(t);
				}
			}
		}
		return modified;
	}
}

package ar.edu.itba.it.dev.common.jpa.domain;

import java.lang.reflect.Constructor;

import com.google.common.base.Preconditions;

public class ReflectionUtils {

	@SuppressWarnings("unchecked")
	public static <T> Constructor<T> getConstructos(Class<T> clazz, Class<?>... classes) {
		Preconditions.checkNotNull(clazz);

		for (Constructor<?> constructor : clazz.getDeclaredConstructors()) {
			if (constructor.getParameterTypes().length == classes.length) {
				boolean match = true;
				for (int i = 1; match && i < classes.length; i++) {
					if (!classes[i].isAssignableFrom(constructor.getParameterTypes()[i])) {
						match = false;
					}
				}
				if (match) {
					constructor.setAccessible(true);
					return (Constructor<T>) constructor;
				}
			}
		}
		throw new RuntimeException("Undefined constructor for "	+ clazz.getSimpleName() + " receiving given parameter" + clazz);
	}
	
	public static <S> S newInstance(Class<S> clazz) {
		try {
			Constructor<S> constructor = clazz.getDeclaredConstructor();
			constructor.setAccessible(true);
			return constructor.newInstance();
		} catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

}
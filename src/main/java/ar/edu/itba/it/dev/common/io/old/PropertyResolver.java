package ar.edu.itba.it.dev.common.io.old;

import java.lang.reflect.Field;

import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.util.string.Strings;

public final class PropertyResolver {

	private final static int RETURN_NULL = 0;
	private final static int CREATE_NEW_VALUE = 1;
	private final static int RESOLVE_CLASS = 2;

	private PropertyResolver() {
	}

	/**
	 * Looks up the value from the object with the given expression. If the expression, the object itself or one property evaluates to null then a null will be
	 * returned.
	 * 
	 * @param expression The expression string with the property to be lookup.
	 * @param object The object which is evaluated.
	 * @return The value that is evaluated. Null something in the expression evaluated to null.
	 */
	public final static Object getValue(final String expression, final Object object) {
		if (expression == null || expression.equals("") || object == null) {
			return object;
		}

		ObjectAndGetSetter getter = getObjectAndGetSetter(expression, object, RETURN_NULL);
		if (getter == null) {
			return null;
		}

		return getter.getValue();
	}

	/**
	 * Set the value on the object with the given expression. If the expression can't be evaluated then a WicketRuntimeException will be thrown. If a null
	 * object is encountered then it will try to generate it by calling the default constructor and set it on the object.
	 * 
	 * @param expression The expression string with the property to be set.
	 * @param object The object which is evaluated to set the value on.
	 * @param value The value to set.
	 */
	public final static void setValue(final String expression, final Object object, final Object value) {
		if (expression == null || expression.equals("")) {
			throw new RuntimeException("Empty expression setting value: " + value + " on object: " + object);
		}
		if (object == null) {
			throw new RuntimeException("Attempted to set property value on a null object. Property expression: " + expression + " Value: " + value);
		}

		ObjectAndGetSetter setter = getObjectAndGetSetter(expression, object, RETURN_NULL);
		if (setter == null) {
			throw new RuntimeException("Null object returned for expression: " + expression + " for setting value: " + value + " on: " + object);
		}
		setter.setValue(value);
	}

	/**
	 * @param expression
	 * @param object
	 * @return class of the target property object
	 */
	public final static Class<?> getPropertyClass(final String expression, final Object object) {
		ObjectAndGetSetter setter = getObjectAndGetSetter(expression, object, RESOLVE_CLASS);
		if (setter == null) {
			throw new WicketRuntimeException("Null object returned for expression: " + expression + " for getting the target classs of: " + object);
		}
		return setter.getTargetClass();
	}

	/**
	 * @param <T>
	 * @param expression
	 * @param clz
	 * @return class of the target Class property expression
	 */
	@SuppressWarnings("unchecked")
	public static <T> Class<T> getPropertyClass(final String expression, final Class<?> clz) {
		ObjectAndGetSetter setter = getObjectAndGetSetter(expression, null, RESOLVE_CLASS, clz);
		if (setter == null) {
			throw new WicketRuntimeException("No Class returned for expression: " + expression + " for getting the target classs of: " + clz);
		}
		return (Class<T>) setter.getTargetClass();
	}

	/**
	 * @param expression
	 * @param object
	 * @return Field for the property expression or null if such field doesn't exist (only getters and setters)
	 */
	public final static Field getPropertyField(final String expression, final Object object) {
		ObjectAndGetSetter setter = getObjectAndGetSetter(expression, object, RESOLVE_CLASS);
		if (setter == null) {
			throw new WicketRuntimeException("Null object returned for expression: " + expression + " for getting the target classs of: " + object);
		}
		return setter.getField();
	}

	/**
	 * Just delegating the call to the original getObjectAndGetSetter passing the object type as parameter.
	 * 
	 * @param expression
	 * @param object
	 * @param tryToCreateNull
	 * @return {@link ObjectAndGetSetter}
	 */
	private static ObjectAndGetSetter getObjectAndGetSetter(final String expression, final Object object, int tryToCreateNull) {
		return getObjectAndGetSetter(expression, object, tryToCreateNull, object.getClass());
	}

	/**
	 * Receives the class parameter also, since this method can resolve the type for some expression, only knowing the target class
	 * 
	 * @param expression
	 * @param object
	 * @param tryToCreateNull
	 * @param clz
	 * @return {@link ObjectAndGetSetter}
	 */
	private static ObjectAndGetSetter getObjectAndGetSetter(final String expression, final Object object, final int tryToCreateNull, Class<?> clz) {
		String expressionBracketsSeperated = Strings.replaceAll(expression, "[", ".[").toString();
		int index = getNextDotIndex(expressionBracketsSeperated, 0);
		while (index == 0 && expressionBracketsSeperated.startsWith(".")) {
			// eat dots at the beginning of the expression since they will confuse
			// later steps
			expressionBracketsSeperated = expressionBracketsSeperated.substring(1);
			index = getNextDotIndex(expressionBracketsSeperated, 0);
		}
		int lastIndex = 0;
		Object value = object;
		String exp = expressionBracketsSeperated;
		while (index != -1) {
			exp = expressionBracketsSeperated.substring(lastIndex, index);
			if (exp.length() == 0) {
				exp = expressionBracketsSeperated.substring(index + 1);
				break;
			}

			IGetAndSet getAndSetter = null;
			try {
				getAndSetter = getGetAndSetter(exp, clz);
			} catch (WicketRuntimeException ex) {
				// expression by it self can't be found. try to find a
				// setPropertyByIndex(int,value) method
				index = getNextDotIndex(expressionBracketsSeperated, index + 1);
				if (index != -1) {
					String indexExpression = expressionBracketsSeperated.substring(lastIndex, index);
					getAndSetter = getGetAndSetter(indexExpression, clz);
				} else {
					exp = expressionBracketsSeperated.substring(lastIndex);
					break;
				}
			}
			Object newValue = null;
			if (value != null) {
				newValue = getAndSetter.getValue(value);
			}
			if (newValue == null) {
				if (tryToCreateNull == CREATE_NEW_VALUE) {
					newValue = getAndSetter.newValue(value);
					if (newValue == null) {
						return null;
					}
				} else if (tryToCreateNull == RESOLVE_CLASS) {
					clz = getAndSetter.getTargetClass();
				} else {
					return null;
				}
			}
			value = newValue;
			if (value != null) {
				// value can be null if we are in the RESOLVE_CLASS
				clz = value.getClass();
			}

			lastIndex = index + 1;
			index = getNextDotIndex(expressionBracketsSeperated, lastIndex);
			if (index == -1) {
				exp = expressionBracketsSeperated.substring(lastIndex);
				break;
			}
		}
		IGetAndSet getAndSetter = getGetAndSetter(exp, clz);
		return new ObjectAndGetSetter(getAndSetter, value);
	}

	/**
	 * 
	 * @param expression
	 * @param start
	 * @return next dot index
	 */
	private static int getNextDotIndex(final String expression, final int start) {
		boolean insideBracket = false;
		for (int i = start; i < expression.length(); i++) {
			char ch = expression.charAt(i);
			if (ch == '.' && !insideBracket) {
				return i;
			} else if (ch == '[') {
				insideBracket = true;
			} else if (ch == ']') {
				insideBracket = false;
			}
		}
		return -1;
	}

	private final static IGetAndSet getGetAndSetter(String exp, final Class<?> clz) {
		Field field = findField(clz, exp);
		if (field != null) {
			return new FieldGetAndSetter(field);
		} else {
			throw new RuntimeException("The expression '" + exp + "' is neither an index nor is it a method or field for the list " + clz);
		}
	}

	/**
	 * @param clz
	 * @param expression
	 * @return introspected field
	 */
	private static Field findField(final Class<?> clz, final String expression) {
		Field field = null;
		try {
			field = clz.getField(expression);
		} catch (Exception e) {
			Class<?> tmp = clz;
			while (tmp != null && tmp != Object.class) {
				Field[] fields = tmp.getDeclaredFields();
				for (Field aField : fields) {
					if (aField.getName().equals(expression)) {
						aField.setAccessible(true);
						return aField;
					}
				}
				tmp = tmp.getSuperclass();
			}
			throw new RuntimeException("Cannot find field " + clz + "." + expression);
		}
		return field;
	}

	private final static class ObjectAndGetSetter {
		private final IGetAndSet getAndSetter;
		private final Object value;

		/**
		 * @param getAndSetter
		 * @param value
		 */
		public ObjectAndGetSetter(IGetAndSet getAndSetter, Object value) {
			this.getAndSetter = getAndSetter;
			this.value = value;
		}

		/**
		 * @param value
		 * @param converter
		 */
		public void setValue(Object value) {
			getAndSetter.setValue(this.value, value);
		}

		/**
		 * @return The value
		 */
		public Object getValue() {
			return getAndSetter.getValue(value);
		}

		/**
		 * @return class of property value
		 */
		public Class<?> getTargetClass() {
			return getAndSetter.getTargetClass();
		}

		/**
		 * @return Field or null if no field exists for expression
		 */
		public Field getField() {
			return getAndSetter.getField();
		}
	}

	public static interface IGetAndSet {

		/**
		 * @return The target class of the object that as to be set.
		 */
		public Class<?> getTargetClass();

		/**
		 * @param object The object where the new value must be set on.
		 * 
		 * @return The new value for the property that is set back on that object.
		 */
		public Object newValue(Object object);

		/**
		 * @param object The object where the value must be taken from.
		 * 
		 * @return The value of this property
		 */
		public Object getValue(final Object object);

		/**
		 * @param object
		 * @param value
		 * @param converter
		 */
		public void setValue(final Object object, final Object value);

		/**
		 * @return Field or null if there is no field
		 */
		public Field getField();
	}

	private static abstract class AbstractGetAndSet implements IGetAndSet {

		@Override
		public Field getField() {
			return null;
		}

		@Override
		public Class<?> getTargetClass() {
			return null;
		}
	}

	private static class FieldGetAndSetter extends AbstractGetAndSet {

		private final Field field;

		public FieldGetAndSetter(final Field field) {
			this.field = field;
			this.field.setAccessible(true);
		}

		@Override
		public Object getValue(final Object object) {
			try {
				return field.get(object);
			} catch (Exception ex) {
				throw new WicketRuntimeException("Error getting field value of field " + field + " from object " + object, ex);
			}
		}

		@Override
		public Object newValue(final Object object) {
			Class<?> clz = field.getType();
			Object value = null;
			try {
				value = clz.newInstance();
				field.set(object, value);
			} catch (Throwable t) {
				throw new RuntimeException(t);
			}
			return value;
		}

		@Override
		public void setValue(final Object object, Object value) {
			try {
				field.set(object, value);
			} catch (Exception ex) {
				throw new WicketRuntimeException("Error setting field value of field " + field + " on object " + object + ", value " + value, ex);
			}
		}

		@Override
		public Class<?> getTargetClass() {
			return field.getType();
		}

		@Override
		public Field getField() {
			return field;
		}
	}
}

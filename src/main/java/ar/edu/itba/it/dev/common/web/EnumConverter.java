package ar.edu.itba.it.dev.common.web;

import java.util.Locale;

import org.apache.wicket.Application;
import org.apache.wicket.Component;
import org.apache.wicket.util.convert.IConverter;

/**
 * Base class for converters that provide a representation of enum values.
 * The idea behind this converter is to use Locale aware resource loading 
 * already present in wicket, to resolve the enum values into strings.
 * <p>
 * For that, it is required that, at application property level, a set of properties
 * are defined, one for each possible value, with the expected translation.
 * </p> 
 */
public class EnumConverter<T extends Enum<T>> implements IConverter<Enum<T>> {
	
	private final Class<T> type;
	private final String prefix;
	private final Component component;
	
	public static <T extends Enum<T>> EnumConverter<T> newInstance(Class<T> type) {
		return newInstance(type, null);
	}
	
	public static <T extends Enum<T>> EnumConverter<T> newInstance(Class<T> type, Component component) {
		return new EnumConverter<T>(type, component);
	}

	private EnumConverter(Class<T> type) {
		this(type, null);
	}
	
	private EnumConverter(Class<T> type, Component component) {
		this.type = type;
		this.prefix = type.getSimpleName() + ".";
		this.component = component;
	}


	@Override
	public Enum<T> convertToObject(String value, Locale locale) {
		for (T kind : type.getEnumConstants()) {
			if (convertToString(kind, locale).equals(value)) {
				return kind;
			}
		}
		return null;
	}

	@Override
	public String convertToString(Enum<T> value, Locale locale) {
		if (value== null) {
			return "";
		}
		return Application.get().getResourceSettings().getLocalizer().getString(prefix + value.toString(), component, null, null);
	}
}

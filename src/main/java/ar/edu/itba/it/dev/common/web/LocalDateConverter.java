package ar.edu.itba.it.dev.common.web;


import java.util.Date;
import java.util.Locale;

import org.apache.wicket.datetime.PatternDateConverter;
import org.apache.wicket.util.convert.ConversionException;
import org.apache.wicket.util.convert.IConverter;
import org.apache.wicket.util.string.Strings;
import org.joda.time.LocalDate;

/**
 * Default converter for LocalDate. Will represent dates as dd/mm/yyyy
 * @author Pablo Abad (pabad@itba.edu.ar)
 */
public class LocalDateConverter implements IConverter<LocalDate> {
	private static final long serialVersionUID = 1L;
    public static final String DATE_FORMAT = "dd/MM/yyyy";

	private final PatternDateConverter converter;
	
	
	public LocalDateConverter() {
		converter = new PatternDateConverter(DATE_FORMAT, true);
	}
	
	@Override
	public LocalDate convertToObject(String value, Locale locale) {
		Date date = converter.convertToObject(value, locale);
		if (!Strings.isEmpty(value) && !value.matches(".*/\\d{4}$")){
			ConversionException ex = new ConversionException("Date must have 4 digits year");
			ex.setResourceKey("Validation.DateMustHave4DigitsYear");
			throw ex;
		}
		return date == null ? null : new LocalDate(date);
	}

	@Override
	public String convertToString(LocalDate value, Locale locale) {
		return converter.convertToString(value.toDateTimeAtStartOfDay().toDate(), locale);
	}
}

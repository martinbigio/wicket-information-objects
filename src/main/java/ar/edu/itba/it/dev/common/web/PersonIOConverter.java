package ar.edu.itba.it.dev.common.web;

import java.util.Locale;

import org.apache.wicket.util.convert.ConversionException;
import org.apache.wicket.util.convert.IConverter;

import ar.edu.itba.it.dev.io.backend.domain.Person;
import ar.edu.itba.it.dev.io.backend.domain.PersonIO;
import ar.edu.itba.it.dev.io.backend.domain.PersonRepo;

import com.google.inject.Inject;

public class PersonIOConverter implements IConverter<PersonIO>{

	@Inject private PersonRepo people;
	
	@Override
	public PersonIO convertToObject(String value, Locale locale) {
		if (value == null || value.equals("")) {
			return null;
		}
		
		Person person = people.get(value);
		if (person == null) {
			throw new ConversionException("Undefined person " + value).setSourceValue(value)
					.setTargetType(PersonIO.class)
					.setConverter(this)
					.setLocale(locale);
		}
		
		return person.io;
	}

	@Override
	public String convertToString(PersonIO value, Locale locale) {
		return value.lastName + ", " + value.firstName;
	}
}
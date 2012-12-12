package ar.edu.itba.it.dev.common.web;

import java.util.Locale;

import org.apache.wicket.util.convert.ConversionException;
import org.apache.wicket.util.convert.IConverter;

import ar.edu.itba.it.dev.io.backend.domain.PersonIO;
import ar.edu.itba.it.dev.io.backend.domain.Subject;
import ar.edu.itba.it.dev.io.backend.domain.SubjectIO;
import ar.edu.itba.it.dev.io.backend.domain.SubjectRepo;

import com.google.inject.Inject;

public class SubjectIOConverter implements IConverter<SubjectIO>{

	@Inject private SubjectRepo subjects;
	
	@Override
	public SubjectIO convertToObject(String value, Locale locale) {
		if (value == null || value.equals("")) {
			return null;
		}
		
		Subject subject = subjects.get(value);
		if (subjects == null) {
			throw new ConversionException("Undefined subject " + value).setSourceValue(value)
					.setTargetType(PersonIO.class)
					.setConverter(this)
					.setLocale(locale);
		}
		
		return subject.io;
	}

	@Override
	public String convertToString(SubjectIO value, Locale locale) {
		return value.desc;
	}
}
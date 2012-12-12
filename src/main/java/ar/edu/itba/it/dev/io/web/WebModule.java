package ar.edu.itba.it.dev.io.web;

import org.apache.wicket.protocol.http.WebApplication;

import ar.edu.itba.it.dev.common.web.PersonIOConverter;
import ar.edu.itba.it.dev.common.web.SubjectIOConverter;
import ar.edu.itba.it.dev.common.web.io.DefaultFormComponentLibrary;
import ar.edu.itba.it.dev.common.web.io.FormComponentLibrary;

import com.google.inject.servlet.ServletModule;

public class WebModule extends ServletModule {

	@Override
	protected void configureServlets() {
		bind(WebApplication.class).to(WicketApplication.class).asEagerSingleton();
		bind(FormComponentLibrary.class).to(DefaultFormComponentLibrary.class);
		bind(PersonIOConverter.class);
		bind(SubjectIOConverter.class);
	}
}
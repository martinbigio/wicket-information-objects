package ar.edu.itba.it.dev.io.web;

import org.apache.wicket.ConverterLocator;
import org.apache.wicket.IConverterLocator;
import org.apache.wicket.RuntimeConfigurationType;
import org.apache.wicket.guice.GuiceComponentInjector;
import org.apache.wicket.protocol.http.WebApplication;
import org.joda.time.LocalDate;

import ar.edu.itba.it.dev.common.web.AttachBehaviorListener;
import ar.edu.itba.it.dev.common.web.ConverterAwareLocalizer;
import ar.edu.itba.it.dev.common.web.EnumConverter;
import ar.edu.itba.it.dev.common.web.FormRequiredCssBehavior;
import ar.edu.itba.it.dev.common.web.FormStateCssBehavior;
import ar.edu.itba.it.dev.common.web.HibernateRequestCycleListener;
import ar.edu.itba.it.dev.common.web.LocalDateConverter;
import ar.edu.itba.it.dev.common.web.PersonIOConverter;
import ar.edu.itba.it.dev.common.web.SubjectIOConverter;
import ar.edu.itba.it.dev.io.backend.domain.PersonIO;
import ar.edu.itba.it.dev.io.backend.domain.RelativeIO.Type;
import ar.edu.itba.it.dev.io.backend.domain.StudentIO.State;
import ar.edu.itba.it.dev.io.backend.domain.SubjectIO;

import com.google.inject.Inject;
import com.google.inject.Injector;

public class WicketApplication extends WebApplication {
	
	@Inject private Injector injector;
	@Inject private PersonIOConverter personConverter;
	@Inject private SubjectIOConverter subjectConverter;

	public WicketApplication() {
	}
    
	@Override
	public void init() {
        super.init();
        
        getComponentPreOnBeforeRenderListeners().add(new AttachBehaviorListener(new FormRequiredCssBehavior(), new FormStateCssBehavior()));

        getComponentInstantiationListeners().add(new GuiceComponentInjector(this, injector));
        getRequestCycleListeners().add(injector.getInstance(HibernateRequestCycleListener.class));
        getResourceSettings().setLocalizer(new ConverterAwareLocalizer());
        getMarkupSettings().setStripWicketTags(true);
	}
	
	@Override
	public RuntimeConfigurationType getConfigurationType() {
		return RuntimeConfigurationType.DEVELOPMENT;
	}
    	
	@Override
	public Class<ListStudentsPage> getHomePage() {
		return ListStudentsPage.class;
	}
	
	@Override
	protected IConverterLocator newConverterLocator() {
		ConverterLocator locator = new ConverterLocator();
		locator.set(LocalDate.class, new LocalDateConverter());
		locator.set(PersonIO.class, personConverter);
		locator.set(SubjectIO.class, subjectConverter);
		locator.set(LocalDate.class, new LocalDateConverter());
		locator.set(State.class, EnumConverter.newInstance(State.class));
		locator.set(Type.class, EnumConverter.newInstance(Type.class));
		return locator;
	}

	public static WicketApplication get() {
		return (WicketApplication) WebApplication.get();
	}
}
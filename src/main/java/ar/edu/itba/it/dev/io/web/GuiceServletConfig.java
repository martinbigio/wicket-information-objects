package ar.edu.itba.it.dev.io.web;

import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletContext;

import org.apache.wicket.protocol.http.WicketFilter;

import ar.edu.itba.it.dev.common.web.HomeRedirectServlet;
import ar.edu.itba.it.dev.common.web.WicketGuiceFilter;
import ar.edu.itba.it.dev.io.backend.BackendModule;

import com.google.common.collect.ImmutableMap;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.name.Names;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.util.Modules;

public class GuiceServletConfig extends GuiceServletContextListener {
	private final static String APP_ROOT = "/app2";

	@Override
	protected Injector getInjector() {
		return Guice.createInjector(Modules.override(new BackendModule()).with(new WebModule() {
			@Override
			protected void configureServlets() {
				super.configureServlets();
				// Define context init parameters so that they override setup.properties if present
				Properties contextParameters = getContextParameters();
				Names.bindProperties(binder(), contextParameters);

				bindConstant().annotatedWith(Names.named("homeUrl")).to(APP_ROOT.substring(1) + "/");
				bindConstant().annotatedWith(Names.named("maxAge")).to(604800); // 1 week
				serve("/").with(HomeRedirectServlet.class);

				Map<String, String> params = ImmutableMap.of(WicketFilter.FILTER_MAPPING_PARAM, APP_ROOT+ "/*"); 
				filter(APP_ROOT + "/*").through(WicketGuiceFilter.class, params);
			}

			@SuppressWarnings("unchecked")
			private Properties getContextParameters() {
				Properties prop = new Properties();
				ServletContext ctx = getServletContext();
				if (ctx != null) {
					for (Enumeration<String> e = ctx.getInitParameterNames(); e.hasMoreElements();) {
						String key = e.nextElement();
						prop.setProperty(key, ctx.getInitParameter(key));
					}
				}
				return prop;
			}
		}));
	}
}

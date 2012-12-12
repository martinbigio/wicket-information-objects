package ar.edu.itba.it.dev.common.web;

import javax.servlet.FilterConfig;
import javax.servlet.ServletException;

import org.apache.wicket.protocol.http.IWebApplicationFactory;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.protocol.http.WicketFilter;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Wicket filter extension that gets creted by an injector and provides
 * the web application passed as parameter.
 * <p> This class just adapts WicketFilter so that the filter itself may be created by a guice module </p>
 */
@Singleton
public class WicketGuiceFilter extends WicketFilter {
	private final WebApplication webApplication;
	
	@Inject 
	public WicketGuiceFilter(WebApplication webApplication) {
		super();
		this.webApplication = webApplication;
	}

	@Override
	public void init(boolean isServlet, FilterConfig filterConfig)
			throws ServletException {
		super.init(isServlet, filterConfig);
	}
	
	@Override
	protected IWebApplicationFactory getApplicationFactory() {
		return new IWebApplicationFactory() {
			@Override
			public void destroy(WicketFilter filter) {
				return;
			}
			
			@Override
			public WebApplication createApplication(WicketFilter filter) {
				return webApplication;
			}
		};
	}
}

package ar.edu.itba.it.dev.common.web;

import org.apache.log4j.Logger;
import org.apache.wicket.Application;
import org.apache.wicket.MetaDataKey;
import org.apache.wicket.RuntimeConfigurationType;
import org.apache.wicket.protocol.http.PageExpiredException;
import org.apache.wicket.protocol.http.WebSession;
import org.apache.wicket.protocol.http.request.WebClientInfo;
import org.apache.wicket.request.IRequestHandler;
import org.apache.wicket.request.cycle.AbstractRequestCycleListener;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.handler.PageProvider;
import org.apache.wicket.request.handler.RenderPageRequestHandler;

import ar.edu.itba.it.dev.common.jpa.session.PersistenceSessionManager;
import ar.edu.itba.it.dev.io.web.InternalErrorPage;
import ar.edu.itba.it.dev.io.web.WicketApplication;

import com.google.inject.Inject;

public class HibernateRequestCycleListener extends AbstractRequestCycleListener {
	private static final String INVALID_URL_MESSAGE = "Invalid URL";
	private static final Logger logger = Logger.getLogger(WicketApplication.class);
	private static final MetaDataKey<Boolean> HAS_ERROR = new MetaDataKey<Boolean>() { };
	
	private final PersistenceSessionManager manager;
	
	@Inject
	public HibernateRequestCycleListener(PersistenceSessionManager manager) {
		this.manager = manager;
	}

	@Override
	public void onBeginRequest(RequestCycle cycle) {
		cycle.setMetaData(HAS_ERROR, false);
		manager.begin();
	}

	@Override
	public void onEndRequest(RequestCycle cycle) {
		if (!cycle.getMetaData(HAS_ERROR)) {
			manager.commit();
		}
	}
	

	@Override
	public IRequestHandler onException(RequestCycle cycle, Exception ex) {
		cycle.setMetaData(HAS_ERROR, true);
		manager.rollback();
    	if (Application.get().getConfigurationType().equals(RuntimeConfigurationType.DEPLOYMENT)) {
    		StringBuilder error = new StringBuilder();
    		WebClientInfo clientInfo = WebSession.get().getClientInfo();
    		if (clientInfo != null) {
    			error.append("User agent: ");
    			error.append(clientInfo.getUserAgent());
    			error.append("\n");
    		}
    		if (!(ex instanceof PageExpiredException) && !ex.getMessage().equals(INVALID_URL_MESSAGE)) {
    			logger.fatal(error, ex);
    		}
    		
    		return new RenderPageRequestHandler(new PageProvider(new InternalErrorPage()));
    	}
    	return null;
    }
}

/*
 * Copyright (c) 2010 IT - ITBA -- All rights reserved
 */ 
package ar.edu.itba.it.dev.common.jpa.hibernate;

import java.io.Serializable;

import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;

import com.google.inject.Inject;
import com.google.inject.Injector;

/**
 * Autowire interceptor for hibernate objects
 * <p> This interceptor will inject dependencies into entities retrieved from the
 * database </p>  
 * <p> In order to work, it must be registered as a bean in a context (so that it 
 * receives a reference to the application context), and registered as an interceptor
 * in Hibernate configuration </p>
 * </p>
 */
public class HibernateAutoWireInterceptor extends EmptyInterceptor {
	private final Injector injector;
	
	@Inject 
	public HibernateAutoWireInterceptor(Injector injector) {
		super();
		this.injector = injector;
	}

	@Override
	public boolean onLoad(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
		injector.injectMembers(entity);
		return true;
	}
}

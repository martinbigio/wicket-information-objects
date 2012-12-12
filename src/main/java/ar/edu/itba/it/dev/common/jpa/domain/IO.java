/*
 * Copyright (c) 2008 IT - ITBA -- All rights reserved
 */ 
package ar.edu.itba.it.dev.common.jpa.domain;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import ar.edu.itba.it.dev.common.io.annotations.Hidden;

/**
 * Persistent entity base class.
 * <p>
 * This class is to be extended by classes that should be stored in 
 * a persistent location, and do not have a name associated with them.
 * What this class adds, is a unique generated ID.
 * </p>
 */
@MappedSuperclass
public class IO implements Identifiable {

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id @Hidden
	public Integer id;
	
	public IO() {
	}

	@Override
	public Integer getId() {
		return id;
	}
	
	public boolean isNew() {
		return (getId() == null);
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	/**
	 * Returns the actual class, regardless of proxies
	 * @return
	 */
	public Class<?> getRealClass() {
		return this.getClass();
	}
	
	public static <T extends APIObject> T build(Class<T> clazz, IO io) {
		try {
			return ReflectionUtils.getConstructos(clazz, IO.class).newInstance(io);
		} catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}
}
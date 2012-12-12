/*
 * Copyright (c) 2009 IT - ITBA -- All rights reserved
 */
package ar.edu.itba.it.dev.common.web;

import java.util.Arrays;
import java.util.List;

import org.apache.wicket.model.LoadableDetachableModel;

import com.google.common.base.Preconditions;


/**
 * Simple detachable model that provides the list of values specified in an enum
 */
public class EnumValuesModel<T extends Enum<?>> extends LoadableDetachableModel<List<? extends T>> {
	private static final long serialVersionUID = 1L;
	private Class<T> enumClass; 
	
	public static <T extends Enum<?>> EnumValuesModel<T> create(Class<T> enumClass) {
		return new EnumValuesModel<T>(enumClass);
	}
	
	public EnumValuesModel(Class<T> enumClass) {
		super();
		Preconditions.checkNotNull(enumClass);
		this.enumClass = enumClass;
	}

	@Override
	protected List<T> load() {
		return Arrays.asList(enumClass.getEnumConstants()); 
	}


}

/*
 * Copyright (c) 2008 IT - ITBA -- All rights reserved
 */ 
package ar.edu.itba.it.dev.common.jpa.domain;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.Size;

import com.google.common.base.Preconditions;

/**
 * Base persistent entity that has a unique id and a name
 *
 * equals and hashCode methods are defined based on type and id. Note that 
 * type match is exact. Two classes implementing different descendants and 
 * having the same id will not match the equals operation.
 */
@MappedSuperclass
public abstract class PersistentEntity extends IO {
	
	@Column(unique=true, nullable=false,length=100)
	@Size(max=100)
	protected String name;
	
	protected PersistentEntity() {
		super();
	}

	protected PersistentEntity(String name) {
		Preconditions.checkNotNull(name);
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof PersistentEntity))
			return false;
		final PersistentEntity other = (PersistentEntity) obj;
		if (getName() == null) {
			if (other.getName() != null)
				return false;
		} else if (!getName().equals(other.getName()))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return this.getClass().getSimpleName() + " [" + getName() + "]";
	}
}

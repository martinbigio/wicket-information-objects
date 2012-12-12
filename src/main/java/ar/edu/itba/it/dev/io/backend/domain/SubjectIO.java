package ar.edu.itba.it.dev.io.backend.domain;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import ar.edu.itba.it.dev.common.io.annotations.Required;
import ar.edu.itba.it.dev.common.jpa.domain.IO;

@Entity
public class SubjectIO extends IO {

	@NotNull @Size(max = 100)
	@Required
	public String code;

	@NotNull @Size(max = 100)
	@Required
	public String name;
	
	@NotNull @Size(max = 200)
	@Required
	public String desc;
}
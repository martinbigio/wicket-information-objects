package ar.edu.itba.it.dev.io.backend.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Enumerated;
import javax.validation.constraints.Size;

import ar.edu.itba.it.dev.common.io.annotations.Required;

@Embeddable
public class RelativeIO {

	public static enum Type {FATHER, MOTHER}
	
	@Enumerated
	@Column(length = 50, nullable = false)
	@Required
	public Type type;
	
	@Column(length = 100, nullable = false)
	@Size(max = 100)
	@Required
	public String firstName;

	@Column(length = 100, nullable = false)
	@Size(max = 100)
	@Required
	public String lastName;

	RelativeIO() {
	}
	
	RelativeIO(Type type, String firstName, String lastName) {
		this.type = type;
		this.firstName = firstName;
		this.lastName = lastName;
	}
}
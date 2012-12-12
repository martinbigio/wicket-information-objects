package ar.edu.itba.it.dev.io.backend.domain;

import ar.edu.itba.it.dev.common.io.ObjectDefinition;
import ar.edu.itba.it.dev.common.io.IOViewerBuilder;
import ar.edu.itba.it.dev.common.jpa.domain.APIObject;

public class Person implements APIObject {

	public final PersonIO io;

	public Person(PersonIO io) {
		this.io = io;
	}

	public static ObjectDefinition viewer() {
		return IOViewerBuilder.forClass(PersonIO.class).build();
	}
}
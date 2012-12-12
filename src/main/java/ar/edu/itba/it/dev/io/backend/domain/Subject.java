package ar.edu.itba.it.dev.io.backend.domain;

import ar.edu.itba.it.dev.common.io.ObjectDefinition;
import ar.edu.itba.it.dev.common.io.IOViewerBuilder;
import ar.edu.itba.it.dev.common.jpa.domain.APIObject;

public class Subject implements APIObject {
	
	public final SubjectIO io;

	public Subject(SubjectIO io) {
		this.io = io;
	}
	
	public ObjectDefinition viewer() {
		return IOViewerBuilder.forClass(SubjectIO.class).build();
	}
}
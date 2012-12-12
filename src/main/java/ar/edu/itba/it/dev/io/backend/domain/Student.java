package ar.edu.itba.it.dev.io.backend.domain;

import com.google.common.base.Preconditions;

import ar.edu.itba.it.dev.common.io.ObjectDefinition;
import ar.edu.itba.it.dev.common.io.IOViewerBuilder;
import ar.edu.itba.it.dev.common.io.annotations.Narrowed;
import ar.edu.itba.it.dev.common.jpa.domain.APIObject;

public class Student implements APIObject {
	
	public final StudentIO io;

	public Student(StudentIO io) {
		this.io = io;
	}
	
	public void add(Subject s) {
		io.subjects.add(Preconditions.checkNotNull(s).io);
	}
	
	public static ObjectDefinition viewer() {
		return IOViewerBuilder.forClass(StudentIO.class).build();
	}
	
	public static ObjectDefinition narrowedViewer() {
		return IOViewerBuilder
				.forClass(StudentIO.class)
				.override(PersonIO.class).with(Narrowed.class)
				.build();
	}
}
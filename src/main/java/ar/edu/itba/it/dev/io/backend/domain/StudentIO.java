package ar.edu.itba.it.dev.io.backend.domain;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import ar.edu.itba.it.dev.common.io.annotations.Narrowed;
import ar.edu.itba.it.dev.common.io.annotations.Required;
import ar.edu.itba.it.dev.common.jpa.domain.IO;

import com.google.common.collect.Sets;

@Entity
public class StudentIO extends IO {

	public static enum State {REGULAR, STAND_BY, GRADUATED, QUIT}

	@NotNull @Size(max=50)
	@Required 
	public String identification;
	
	@Enumerated(EnumType.STRING)
	@NotNull @Size(max = 50)
	@Required
	public State state;
	
	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "StudentSubjects", joinColumns = @JoinColumn(name = "studentId"), inverseJoinColumns = @JoinColumn(name = "subjectId"))
	@Narrowed
	public Set<SubjectIO> subjects = Sets.newHashSet();
	
	@OneToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "personId", nullable = false)
	@Required
	public PersonIO person = new PersonIO();
}
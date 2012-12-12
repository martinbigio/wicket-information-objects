package ar.edu.itba.it.dev.io.backend.domain;

import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import ar.edu.itba.it.dev.common.io.annotations.Required;
import ar.edu.itba.it.dev.common.jpa.domain.IO;

import com.google.common.collect.Lists;

@Entity
public class PersonIO extends IO {

	@NotNull @Size(max = 100)
	@Required
	public String firstName;

	@NotNull @Size(max = 100)
	@Required
	public String lastName;
	
	@Embedded
	public AddressIO address = new AddressIO();
	
	@NotNull
	@Required
	public Integer age;
//
//	@Required
//	public LocalDate bornDate;
//	
	@ElementCollection
	@JoinTable(name = "PersonRelatives", joinColumns = @JoinColumn(name = "personId"))
	public List<RelativeIO> relatives = Lists.newArrayList();
	
	@ElementCollection
	@JoinTable(name = "PersonHobbies", joinColumns = @JoinColumn(name = "personId"))
	public List<String> hobbies = Lists.newArrayList();
}
package ar.edu.itba.it.dev.io.backend.domain;

import java.util.List;

public interface PersonRepo {
	
	void add(Person person);
	Person get(String name);
	List<Person> matching(String name);
}

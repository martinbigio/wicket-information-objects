package ar.edu.itba.it.dev.io.backend.domain;

import java.util.List;

public interface StudentRepo {
	void add(Student student);
	Student get(String identification);
	List<Student> all();
	void update(Student student);
}

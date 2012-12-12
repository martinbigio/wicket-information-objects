package ar.edu.itba.it.dev.io.backend.domain;

import java.util.List;

public interface SubjectRepo {
	void add(Subject subject);
	Subject get(String desc);
	List<Subject> matching(String desc);
}

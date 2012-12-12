package ar.edu.itba.it.dev.io.backend.domain;

import java.util.List;

import org.hibernate.SessionFactory;

import ar.edu.itba.it.dev.common.jpa.domain.IO;
import ar.edu.itba.it.dev.common.jpa.repo.AbstractHibernateRepo;

import com.google.common.collect.Lists;
import com.google.inject.Inject;

public class HibernateStudentRepo extends AbstractHibernateRepo implements StudentRepo {

	@Inject
	public HibernateStudentRepo(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	@Override
	public void add(Student studnet) {
		persist(studnet.io);
	}
	
	@Override
	public void update(Student student) {
		merge(student.io);
	}

	@Override
	public Student get(String identification) {
		return IO.build(Student.class, (StudentIO) findOne("FROM StudentIO WHERE identification = ?", identification));
	}

	@Override
	public List<Student> all() {
		List<Student> result = Lists.newLinkedList();
		List<StudentIO> ios = findAll("FROM StudentIO");
		for (StudentIO io: ios) {
			result.add(IO.build(Student.class, io));
		}
		return result;
	}
}
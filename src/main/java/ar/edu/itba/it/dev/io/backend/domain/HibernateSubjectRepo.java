package ar.edu.itba.it.dev.io.backend.domain;

import java.util.List;

import org.hibernate.SessionFactory;

import ar.edu.itba.it.dev.common.jpa.domain.IO;
import ar.edu.itba.it.dev.common.jpa.repo.AbstractHibernateRepo;

import com.google.common.collect.Lists;
import com.google.inject.Inject;

public class HibernateSubjectRepo extends AbstractHibernateRepo implements SubjectRepo {

	@Inject
	public HibernateSubjectRepo(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	@Override
	public void add(Subject subject) {
		persist(subject.io);
	}

	@Override
	public Subject get(String desc) {
		return IO.build(Subject.class, (SubjectIO) findOne("FROM SubjectIO s WHERE s.desc like ?", "%" + desc + "%"));
	}
	
	@Override
	public List<Subject> matching(String desc) {
		List<Subject> result = Lists.newLinkedList();
		List<SubjectIO> people = findAll("FROM SubjectIO s WHERE s.desc like ?", "%" + desc + "%");
		for(SubjectIO io: people) {
			result.add(IO.build(Subject.class, io));
		}
		return result;
	}
}
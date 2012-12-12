package ar.edu.itba.it.dev.io.backend.domain;

import java.util.List;

import org.hibernate.SessionFactory;

import com.google.common.collect.Lists;
import com.google.inject.Inject;

import ar.edu.itba.it.dev.common.jpa.domain.IO;
import ar.edu.itba.it.dev.common.jpa.repo.AbstractHibernateRepo;

public class HibernatePersonRepo extends AbstractHibernateRepo implements PersonRepo {

	@Inject
	public HibernatePersonRepo(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	@Override
	public void add(Person person) {
		persist(person.io);
	}
	
	@Override
	public Person get(String name) {
		return IO.build(Person.class, (PersonIO) findOne("FROM PersonIO WHERE lastName + ', ' + firstName = ?", name));
	}


	@Override
	public List<Person> matching(String name) {
		List<Person> result = Lists.newLinkedList();
		List<PersonIO> people = findAll("FROM PersonIO WHERE lastName + ', ' + firstName like ?", "%" + name + "%");
		for(PersonIO io: people) {
			result.add(IO.build(Person.class, io));
		}
		return result;
	}
}

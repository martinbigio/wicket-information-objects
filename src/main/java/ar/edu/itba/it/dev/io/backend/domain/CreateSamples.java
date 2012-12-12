package ar.edu.itba.it.dev.io.backend.domain;

import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.hibernate.context.ManagedSessionContext;

import ar.edu.itba.it.dev.common.jpa.domain.IO;
import ar.edu.itba.it.dev.io.backend.domain.RelativeIO.Type;
import ar.edu.itba.it.dev.io.backend.domain.StudentIO.State;

import com.google.inject.Inject;

public class CreateSamples {


	@Inject
	public CreateSamples(StudentRepo students, SubjectRepo subjects, SessionFactory sessionFactory) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		ManagedSessionContext.bind(session);
		
		for (int i = 0; i < 20; i++) {
			students.add(createStudent(i));
		}
		
		for (int i = 0; i < 5; i++) {
			subjects.add(createSubject(i));
		}
		
		Student s = students.get("0");
		s.io.person.age = 22;
		s.io.person.relatives.add(new RelativeIO(Type.FATHER, "Alejandro", "Romano"));
		s.io.person.relatives.add(new RelativeIO(Type.MOTHER, "Mirta", "Fernadez"));
		s.add(subjects.get("Materia 00.00"));
		s.add(subjects.get("Materia 00.01"));

		s = students.get("1");
		s.io.person.age = 26;
		s.io.person.relatives.add(new RelativeIO(Type.FATHER, "Jorge", "Rodriguez"));
		s.io.person.relatives.add(new RelativeIO(Type.MOTHER, "Roxana", "Mato"));
		s.add(subjects.get("Materia 00.02"));
		s.add(subjects.get("Materia 00.03"));
		s.add(subjects.get("Materia 00.04"));
		
		session.getTransaction().commit();
		session.close();
	}

	private Student createStudent(int num) {
		PersonIO personIO = new PersonIO();
		personIO.firstName = "firstName" + num;
		personIO.lastName = "lastName" + num;
//		personIO.bornDate = new LocalDate(1985, 10, 11);
		personIO.age = 26;

		StudentIO studentIO = new StudentIO();
		studentIO.person = personIO;
		studentIO.identification = String.valueOf(num);
		studentIO.state = State.REGULAR;
		return IO.build(Student.class, studentIO);
	}
	
	private Subject createSubject(int num) {
		SubjectIO io = new SubjectIO();
		io.code = String.format("00.%02d", num);
		io.name = "Materia";
		io.desc = "Materia " + io.code;
		return IO.build(Subject.class, io);
	}

}

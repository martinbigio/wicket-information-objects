package ar.edu.itba.it.dev.io.web;

import java.util.List;

import org.apache.wicket.model.IModel;

import ar.edu.itba.it.dev.common.web.LimitedAutoCompleteTextField;
import ar.edu.itba.it.dev.io.backend.domain.Person;
import ar.edu.itba.it.dev.io.backend.domain.PersonIO;
import ar.edu.itba.it.dev.io.backend.domain.PersonRepo;

import com.google.common.collect.Lists;
import com.google.inject.Inject;

public class PersonTextField extends LimitedAutoCompleteTextField<PersonIO> {

	@Inject private PersonRepo people;

	public PersonTextField(String id, IModel<PersonIO> model) {
		super(id, model, PersonIO.class);
	}

	@Override
	public Iterable<PersonIO> getResults(String input) {
		List<PersonIO> result = Lists.newLinkedList();
		for (Person p: people.matching(input)) {
			result.add(p.io);
		}
		return result;
	}
}

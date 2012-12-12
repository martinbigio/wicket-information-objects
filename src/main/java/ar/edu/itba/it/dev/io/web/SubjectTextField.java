package ar.edu.itba.it.dev.io.web;

import java.util.List;

import org.apache.wicket.model.IModel;

import ar.edu.itba.it.dev.common.web.LimitedAutoCompleteTextField;
import ar.edu.itba.it.dev.io.backend.domain.Subject;
import ar.edu.itba.it.dev.io.backend.domain.SubjectIO;
import ar.edu.itba.it.dev.io.backend.domain.SubjectRepo;

import com.google.common.collect.Lists;
import com.google.inject.Inject;

public class SubjectTextField extends LimitedAutoCompleteTextField<SubjectIO> {

	@Inject private SubjectRepo subjects;

	public SubjectTextField(String id, IModel<SubjectIO> model) {
		super(id, model, SubjectIO.class);
	}

	@Override
	public Iterable<SubjectIO> getResults(String input) {
		List<SubjectIO> result = Lists.newLinkedList();
		for (Subject p: subjects.matching(input)) {
			result.add(p.io);
		}
		return result;
	}
}

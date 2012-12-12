package ar.edu.itba.it.dev.io.web;

import org.apache.wicket.Component;
import org.apache.wicket.Page;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;

import ar.edu.itba.it.dev.common.io.ObjectDefinition;
import ar.edu.itba.it.dev.common.web.BackSupportPage;
import ar.edu.itba.it.dev.common.web.Models;
import ar.edu.itba.it.dev.common.web.components.Wicket;
import ar.edu.itba.it.dev.common.web.components.events.ClickableEventAdapter;
import ar.edu.itba.it.dev.common.web.io.IOForm;
import ar.edu.itba.it.dev.common.web.io.JsonSerializableModel;
import ar.edu.itba.it.dev.io.backend.domain.Student;
import ar.edu.itba.it.dev.io.backend.domain.StudentIO;
import ar.edu.itba.it.dev.io.backend.domain.StudentRepo;

import com.google.common.base.Preconditions;
import com.google.inject.Inject;

public class EditStudentPage extends BackSupportPage<Student> {

	@Inject private StudentRepo students;
	
	private IModel<StudentIO> io;
	
    public EditStudentPage(Page page, Student student) {
    	super(page, Models.newAPIModel(Student.class, Preconditions.checkNotNull(student).io));
    	IModel<ObjectDefinition> viewer = new LoadableDetachableModel<ObjectDefinition>() {
			@Override
			protected ObjectDefinition load() {
				return Student.viewer();
			}
    	};
    	io = new JsonSerializableModel<StudentIO>(StudentIO.class, student().io, viewer);
    	
    	Form<Void> form = new Form<Void>("form");
    	form.add(new IOForm<StudentIO>("fields", io, viewer.getObject()));
    	form.add(Wicket.button("save", "Save", new ClickableEventAdapter() {
    		@Override
    		public void onClick(Component component) {
    			students.update(new Student(io.getObject()));
    			returnToPreviousPage();
    		}
    	}));
    	form.add(Wicket.cancelButton("cancel", "Cancel", new ClickableEventAdapter() {
    		@Override
    		public void onClick(Component component) {
    			returnToPreviousPage();
    		}
    	}));
    	add(form);
	}
    
    private Student student() {
    	return getModelObject();
    }
}
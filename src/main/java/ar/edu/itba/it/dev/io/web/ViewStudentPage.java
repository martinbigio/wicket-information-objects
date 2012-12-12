package ar.edu.itba.it.dev.io.web;

import org.apache.wicket.Component;
import org.apache.wicket.Page;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.PropertyModel;

import ar.edu.itba.it.dev.common.web.BackSupportPage;
import ar.edu.itba.it.dev.common.web.Models;
import ar.edu.itba.it.dev.common.web.components.Wicket;
import ar.edu.itba.it.dev.common.web.components.events.ClickableEventAdapter;
import ar.edu.itba.it.dev.common.web.io.IOPanel;
import ar.edu.itba.it.dev.io.backend.domain.Student;
import ar.edu.itba.it.dev.io.backend.domain.StudentIO;

import com.google.common.base.Preconditions;

public class ViewStudentPage extends BackSupportPage<Student> {

    public ViewStudentPage(Page page, Student student) {
    	super(page, Models.newAPIModel(Student.class, Preconditions.checkNotNull(student).io));
    	add(new IOPanel("panel", new PropertyModel<StudentIO>(getModel(), "io"), Student.viewer()));
    	add(new Form<Void>("form").add(Wicket.cancelButton("back", "Back", new ClickableEventAdapter() {
    		@Override
    		public void onClick(Component component) {
    			returnToPreviousPage();
    		}
    	})));
    }
}
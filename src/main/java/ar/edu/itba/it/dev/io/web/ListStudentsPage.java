package ar.edu.itba.it.dev.io.web;

import java.util.Iterator;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.HeadersToolbar;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.NoRecordsToolbar;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;

import ar.edu.itba.it.dev.common.jpa.filter.EntityStore;
import ar.edu.itba.it.dev.common.web.BasePage;
import ar.edu.itba.it.dev.common.web.actions.AbstractResourceCommonAction;
import ar.edu.itba.it.dev.common.web.components.Wicket;
import ar.edu.itba.it.dev.common.web.components.events.ClickableEventAdapter;
import ar.edu.itba.it.dev.common.web.datatable.ActionColumn;
import ar.edu.itba.it.dev.common.web.datatable.columns.ColumnBuilder;
import ar.edu.itba.it.dev.common.web.images.Images;
import ar.edu.itba.it.dev.io.backend.domain.Student;
import ar.edu.itba.it.dev.io.backend.domain.StudentIO;
import ar.edu.itba.it.dev.io.backend.domain.StudentRepo;

import com.google.common.collect.Lists;
import com.google.inject.Inject;

public class ListStudentsPage extends BasePage<Void> {

	@Inject private EntityStore entities;
	@Inject private StudentRepo students;
	
    public ListStudentsPage() {
    	List<IColumn<Student>> columns = Lists.newArrayList();
    	columns.add(ColumnBuilder.forType(Student.class).withTitle("identification").showing("io.identification").build());
    	columns.add(ColumnBuilder.forType(Student.class).withTitle("person").showing("io.person").build());
    	columns.add(ColumnBuilder.forType(Student.class).withTitle("state").showing("io.state").build());
    	ActionColumn<Student> actions = new ActionColumn<Student>();
    	actions.addAction(new AbstractResourceCommonAction<Student>("details", Images.DETAILS) {
			@Override
			public void onExecute(Component component, IModel<Student> data) {
				setResponsePage(new ViewStudentPage(getPage(), data.getObject()));
			}
		});
    	actions.addAction(new AbstractResourceCommonAction<Student>("edit", Images.EDIT) {
			@Override
			public void onExecute(Component component, IModel<Student> data) {
				setResponsePage(new EditStudentPage(getPage(), data.getObject()));
			}
		});
    	columns.add(actions);

    	
    	DataTable<Student> table = new DataTable<Student>("table", columns, new StudentDataProvider(), Integer.MAX_VALUE);
		table.addTopToolbar(new HeadersToolbar(table, null));
		table.addBottomToolbar(new NoRecordsToolbar(table));
		add(table);
		add(Wicket.link("add", new ClickableEventAdapter() {
			@Override
			public void onClick(Component component) {
				setResponsePage(new AddStudentPage(getPage()));
			}
		}));
    }
    
    private class StudentDataProvider implements IDataProvider<Student> {

		@Override
		public void detach() {
		}

		@Override
		public Iterator<? extends Student> iterator(int first, int count) {
			return students.all().iterator();
		}

		@Override
		public int size() {
			return students.all().size();
		}

		@Override
		public IModel<Student> model(Student student) {
			final int id = student.io.id;
			return new LoadableDetachableModel<Student>() {
				@Override
				protected Student load() {
					return new Student(entities.fetch(StudentIO.class, id));
				}
			};
		}
    }
}
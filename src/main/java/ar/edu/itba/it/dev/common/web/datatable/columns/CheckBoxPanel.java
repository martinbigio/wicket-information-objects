package ar.edu.itba.it.dev.common.web.datatable.columns;

import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;


class CheckBoxPanel extends Panel {

	CheckBoxPanel(String id, IModel<Boolean> model) {
		super(id);
		add(new CheckBox("checkbox", model));
	}
}

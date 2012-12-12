package ar.edu.itba.it.dev.common.web.datatable;

import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;

import ar.edu.itba.it.dev.common.web.actions.IAction;

public class ActionExplanationPanel<T> extends Panel {

	public ActionExplanationPanel(String id, List<IAction<T>> actions) {
		super(id);
		
		boolean showReferences = false;
		for (IAction<T> action: actions) {
			if (action.canExecute()) {
				showReferences = true;
			}
		}
		setVisible(showReferences);

		
		add(new ListView<IAction<T>>("actionList", actions) {
			@Override
			protected void populateItem(ListItem<IAction<T>> item) {
				IAction<T> action = item.getModelObject();
				item.add(new Image("image", action.getImage(null)));
				item.add(new Label("name", action.getDescription(null)));
				item.setVisible(action.canExecute());
			}
		});
	}
}

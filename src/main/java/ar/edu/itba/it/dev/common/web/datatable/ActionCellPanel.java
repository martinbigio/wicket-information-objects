package ar.edu.itba.it.dev.common.web.datatable;

import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.link.AbstractLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import ar.edu.itba.it.dev.common.jpa.ApplicationException;
import ar.edu.itba.it.dev.common.web.TypedPanel;
import ar.edu.itba.it.dev.common.web.actions.IAJAXAction;
import ar.edu.itba.it.dev.common.web.actions.IAction;
import ar.edu.itba.it.dev.common.web.actions.ICommonAction;
import ar.edu.itba.it.dev.common.web.components.Wicket;
import ar.edu.itba.it.dev.common.web.components.behaviors.ConfirmationBehavior;
import ar.edu.itba.it.dev.common.web.components.events.ClickableEventAdapter;
import ar.edu.itba.it.dev.common.web.components.events.FeedbackPanelLocator;

public class ActionCellPanel<T> extends TypedPanel<T> {

	private List<IAction<T>> actions;
	
	public ActionCellPanel(String id, IModel<T> model, List<IAction<T>> actions) {
		super(id, model);
		this.actions = actions;
	}
	
	private IModel<T> rowModel() {
		return getModel();
	}
	
	@Override
	protected void onInitialize() {
		super.onInitialize();
		
		ListView<IAction<T>> list = new ListView<IAction<T>>("actions", actions) {

			@Override
			protected void populateItem(final ListItem<IAction<T>> item) {
				IAction<T> action = item.getModelObject();

				AbstractLink link = null;
				if (item.getModelObject() instanceof ICommonAction<?>) {
					link = Wicket.link("action", rowModel(), new ClickableEventAdapter() {
						@Override
						public void onClick(Component component) {
							((ICommonAction<T>)item.getModelObject()).onExecute(component, rowModel());								
						}
					});
				} else {
					link = new AjaxLink<T>("action", rowModel()) {
						@Override
						public void onClick(AjaxRequestTarget target) {
							try {
								((IAJAXAction<T>) item.getModelObject()).onExecute(this, rowModel(), target);
							} catch (ApplicationException e) {
								String msg = getString(e.getClass().getSimpleName(),
										new Model<ApplicationException>(e));
								error(msg);
								target.add(((FeedbackPanelLocator) getPage()).getFeedbackPanel());
							}
						}
					};
				}
				// PATTERN: [Wicket] when visibility doesn't change dynamically, set it up on construction
				link.setVisible(item.getModelObject().isVisible(rowModel()));
				
				IModel<String> confirmationModel = action.getConfirmationMessage(rowModel());
				if (confirmationModel != null) {
					link.add(new ConfirmationBehavior(confirmationModel));
				}
				item.add(link);

				Image image = new Image("image", action.getImage(rowModel()));
				image.add(AttributeModifier.replace("title", action.getDescription(rowModel())));
				image.add(AttributeModifier.replace("alt", action.getCaption(rowModel())));
				link.add(image);
			}
		};
		add(list);
	}
}

package ar.edu.itba.it.dev.common.web.components.events.ajax;

import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.util.visit.IVisit;
import org.apache.wicket.util.visit.IVisitor;

import com.google.common.base.Preconditions;

public class EventUtils {
	
	public static void addFeedbackPanel(final Component component, final AjaxRequestTarget target) {
		if (component instanceof FeedbackPanel) {
			target.add(component);
		} else {
			MarkupContainer parent = component.getParent();
			/* Pages at least have one FeedbackPanel */
			Preconditions.checkNotNull(parent);
			Component object = parent.visitChildren(FeedbackPanel.class, new IVisitor<FeedbackPanel, Component>() {
				
				@Override
				public void component(FeedbackPanel object, IVisit<Component> visit) {
					target.add(object);
					visit.stop(object);
				}
			});

			if (object == null) {
				addFeedbackPanel(parent, target);
			}
		}
	}
}

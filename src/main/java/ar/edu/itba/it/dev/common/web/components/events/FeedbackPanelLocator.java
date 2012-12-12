package ar.edu.itba.it.dev.common.web.components.events;

import org.apache.wicket.markup.html.panel.FeedbackPanel;

/**
 * Interface implemented by Pages that know how to locate a feedbackPanel compoennt
 * @author Pablo Abad (pabad@itba.edu.ar)
 */
public interface FeedbackPanelLocator {
	/**
	 * Returns the feedback panel
	 * @return The feedback panel
	 */
	public FeedbackPanel getFeedbackPanel();
}

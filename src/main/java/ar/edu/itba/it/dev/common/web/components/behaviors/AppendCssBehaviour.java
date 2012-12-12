package ar.edu.itba.it.dev.common.web.components.behaviors;

import org.apache.wicket.Component;

public class AppendCssBehaviour extends AbstractCssAttributeBehavior {
	
	private String cssClass;
	
	public AppendCssBehaviour(String cssClass) {
		super();
		this.cssClass = cssClass;
	}

	@Override
	protected String getCssClass(Component component) {
		return cssClass;
	}
}

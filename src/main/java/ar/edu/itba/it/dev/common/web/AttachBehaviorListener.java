package ar.edu.itba.it.dev.common.web;

import org.apache.wicket.Component;
import org.apache.wicket.application.IComponentOnBeforeRenderListener;
import org.apache.wicket.behavior.Behavior;

import com.google.common.base.Preconditions;

/**
 * On before render listener that adds a behaviors to all the given components
 * <p>
 * This component facilitiates system-wide behavior attaching, as you can
 * add a listener at the application init function:
 * <pre>
 * public class MyApplication extends WebApplication {
 *    public void init() {
 *       ...
 *       addComponentOnBeforeRenderListener(new AttachBehaviorListener(new GlobalBehavior());
 *    }
 * }
 * </pre>
 * </p>
 */
public class AttachBehaviorListener implements IComponentOnBeforeRenderListener {
	private Behavior[] behaviors;
	
	public AttachBehaviorListener(Behavior... behaviors) {
		super();
		Preconditions.checkArgument(behaviors.length>0, "You should at least specify one behavior to attach!");
		this.behaviors = behaviors;
	}

	@Override
	public final void onBeforeRender(Component component) {
		if (!component.hasBeenRendered()) {
			for (Behavior behavior : behaviors) {
				if (behavior.isEnabled(component) && !component.getBehaviors().contains(behavior)) {
					component.add(behavior);
				}
			}
		}
	}
}

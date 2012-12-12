package ar.edu.itba.it.dev.common.io.old;

import ar.edu.itba.it.dev.common.io.ObjectDefinition;

// right now this interface is unused. Object updating is done using Wicket property models.
public interface IOEditor extends ObjectDefinition {
	void update(String property, Object value);
}

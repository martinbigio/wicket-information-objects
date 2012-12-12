/**
 * 
 */
package ar.edu.itba.it.dev.common.web.datatable.columns;

import org.apache.wicket.request.resource.ResourceReference;

/**
 * Default implementation for the {@link ResourceReferenceResolver} interface
 * By default resolveAlt delegates its return value to resolveText
 * resolveReference and resolveText return null;
 * Subclasses can override desired method to change its return values 
 *
 */
public class DefaultResourceReferenceResolver<T> implements ResourceReferenceResolver<T> {
	
	@Override
	public ResourceReference resolveImage(T item) {
		return null;
	}
	
	@Override
	public String resolveAlt(T item) {
		return resolveText(item);
	}

	@Override
	public String resolveText(T item) {
		return null;
	}
}

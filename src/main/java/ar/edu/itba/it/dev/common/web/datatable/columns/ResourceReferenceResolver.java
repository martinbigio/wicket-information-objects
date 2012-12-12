/**
 * 
 */
package ar.edu.itba.it.dev.common.web.datatable.columns;

import java.io.Serializable;

import org.apache.wicket.request.resource.ResourceReference;


/**
 * {@link ResourceReferenceResolver}. Implemantations for this interface
 * should resolve a {@link ResourceReference}, text and alt for a given value
 *
 */
public interface ResourceReferenceResolver<T> extends Serializable {
	public ResourceReference resolveImage(T item);
	public String resolveText(T item);
	public String resolveAlt(T item);
}

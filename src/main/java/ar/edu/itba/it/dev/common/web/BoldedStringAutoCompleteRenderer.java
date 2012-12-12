package ar.edu.itba.it.dev.common.web;

import org.apache.wicket.extensions.ajax.markup.html.autocomplete.AbstractAutoCompleteRenderer;
import org.apache.wicket.extensions.ajax.markup.html.autocomplete.AbstractAutoCompleteTextRenderer;
import org.apache.wicket.request.Response;

/**
 * Implementation of a AutoCompleteRenderer that encloses the first match of the
 * criteria into a classed span.
 */
public class BoldedStringAutoCompleteRenderer<T> extends AbstractAutoCompleteRenderer<T> {

	private static final long serialVersionUID = 1L;

	/* Force private constructor */
	public BoldedStringAutoCompleteRenderer() {
		super();
	}
	
	/**
	 * @see AbstractAutoCompleteTextRenderer#getTextValue(Object)
	 */
	@Override
	protected String getTextValue(T object) {
		return object.toString();
	}

	@Override
	protected void renderChoice(T object, Response response, String criteria) {
		String textValue = getTextValue(object).toLowerCase();
		int beginIndex = textValue.indexOf(criteria.toLowerCase());
		StringBuffer choice = new StringBuffer();
		/* if we are using a LimitedAutoCompleteTextField this could happen */
		if (beginIndex == -1) {
			choice.append(getTextValue(object));
		} else {
			if (beginIndex > 0) {
				choice.append(getTextValue(object).substring(0, beginIndex));
			}
			choice.append("<span class=\"criteria\">" + getTextValue(object).substring(beginIndex, beginIndex + criteria.length()) + "</span>");
			choice.append(getTextValue(object).substring(beginIndex + criteria.length()));
		}
		response.write(choice);
	}
}

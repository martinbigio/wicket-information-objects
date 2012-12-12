package ar.edu.itba.it.dev.common.web;

import org.apache.wicket.extensions.yui.calendar.DatePicker;
import org.apache.wicket.markup.html.form.AbstractTextComponent.ITextFormatProvider;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.joda.time.LocalDate;

/**
 * Form Textfield that displays Dates (given a LocalDate) and provides a calendar 
 * to aid writing the date
 */
public class LocalDateTextField extends TextField<LocalDate> implements ITextFormatProvider {

	public LocalDateTextField(String id, IModel<LocalDate> model) {
		super(id, model, LocalDate.class);
		init();
	}

	public LocalDateTextField(String id) {
		super(id, LocalDate.class);
		init();
	}

	private void init() {
		// TODO: Datepicker has a bug that makes results being wrong when following an action link in an enhanced datatable 
		// The problem appears when you go back using the browser's button. The list of the enhanced datatable is wrong
		// Fixing this will require understanding what happens in wicket and fixing it.
		
		// Apparently this bug is not present anymore. Perhaps because the filtering links are not
		// using AJAX. It must be tested with AJAX on filtering links. For now, this is uncommented
		// because it works.
		add(new DatePicker() {
			private static final long serialVersionUID = 1L;
			@Override
			protected boolean enableMonthYearSelection() {
				return true;
			}
		});
	}

	@Override
	public String getTextFormat() {
		return LocalDateConverter.DATE_FORMAT;
	}
}

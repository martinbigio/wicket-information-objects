package ar.edu.itba.it.dev.common.web.datatable.columns;

import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.FilterForm;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.joda.time.LocalDate;

import ar.edu.itba.it.dev.common.jpa.LocalDateRange;

public class DateRangePanel extends Panel {

	public DateRangePanel(String id, IModel<LocalDateRange> rangeModel, FilterForm<?> filterForm) {
		super(id, rangeModel);

		DateTextFilter from = new DateTextFilter("from", new PropertyModel<LocalDate>(rangeModel, "from"), filterForm);
		DateTextFilter to = new DateTextFilter("to", new PropertyModel<LocalDate>(rangeModel, "to"), filterForm);
		add(from);
		add(to);
//		filterForm.add(new LocalDateIntervalValidator(from.getFilter(), to.getFilter()));
	}
}

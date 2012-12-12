package ar.edu.itba.it.dev.common.web.io;

import java.util.List;

import org.apache.wicket.MarkupContainer;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.HeadersToolbar;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.NoRecordsToolbar;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;

import ar.edu.itba.it.dev.common.io.ObjectField;
import ar.edu.itba.it.dev.common.io.BaseIOViewer;
import ar.edu.itba.it.dev.common.io.CompoundObjectField;
import ar.edu.itba.it.dev.common.io.ObjectDefinition;
import ar.edu.itba.it.dev.common.io.CollectionObjectField;
import ar.edu.itba.it.dev.common.io.InlineObjectField;
import ar.edu.itba.it.dev.common.web.Models;
import ar.edu.itba.it.dev.common.web.datatable.columns.ColumnBuilder;

import com.google.common.collect.Lists;

@SuppressWarnings({"unchecked", "rawtypes"})
public class IOPanel extends Panel {

	public IOPanel(String id, IModel<?> model, ObjectDefinition viewer) {
		super(id, model);

		RepeatingView fields = new RepeatingView("fields");
		
		for (ObjectField attribute: viewer.getFields()) {
			if (!attribute.isHidden()) {
				if (attribute instanceof CompoundObjectField) {
					fields.add(new CompoundFieldFragment(fields.newChildId(), this, model, (CompoundObjectField) attribute));
				} else if (attribute instanceof CollectionObjectField) {
					fields.add(new CollectionFieldFragment(fields.newChildId(), this, model, (CollectionObjectField) attribute));
				} else if (attribute instanceof InlineObjectField) {
					fields.add(new InlineFieldFragment(fields.newChildId(), this, model, (InlineObjectField) attribute));
				} else {
					throw new UnsupportedOperationException();
				}
			}
		}
		
		add(fields);
	}
	
	private static class InlineFieldFragment extends Fragment {
		public InlineFieldFragment(String id, MarkupContainer markupProvider, IModel<?> base, InlineObjectField field) {
			super(id, "inlineField", markupProvider);
			
			add(new Label("key", getString(field.getKey())));
			add(new Label("value", Models.forAttribute(base, field)));
		}
	}
	
	private static class CompoundFieldFragment extends Fragment {
		public CompoundFieldFragment(String id, MarkupContainer markupProvider, IModel<?> base, CompoundObjectField field) {
			super(id, "compoundField", markupProvider);
			
			add(new Label("key", getString(field.getKey())));
			add(new IOPanel("value", Models.forAttribute(base, field), new BaseIOViewer(field)));
		}
	}
	
	private static class CollectionFieldFragment extends Fragment {
		public CollectionFieldFragment(String id, MarkupContainer markupProvider, IModel<?> base, final CollectionObjectField field) {
			super(id, "collectionField", markupProvider);
			
			add(new Label("key", getString(field.getKey())));
			List<IColumn<?>> columns = Lists.newArrayList();
			for (ObjectField att: field.getFields()) {
				columns.add(ColumnBuilder.forType(null).withTitle(att.getKey()).showing(att.getFieldName()).build());
			}
			DataTable table = new DataTable("table", columns, new IterableDataProvider(new PropertyModel<Iterable<?>>(base, field.getFieldName())), Integer.MAX_VALUE);
			table.addTopToolbar(new HeadersToolbar(table, null));
			table.addBottomToolbar(new NoRecordsToolbar(table));
			add(table);
		}
	}
}
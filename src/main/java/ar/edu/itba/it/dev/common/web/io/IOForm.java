package ar.edu.itba.it.dev.common.web.io;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.validation.constraints.Size;

import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.HeadersToolbar;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.NoRecordsToolbar;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.FormComponentPanel;
import org.apache.wicket.markup.html.form.IFormSubmitter;
import org.apache.wicket.markup.html.form.IFormVisitorParticipant;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.validation.validator.StringValidator;

import ar.edu.itba.it.dev.common.io.BaseIOViewer;
import ar.edu.itba.it.dev.common.io.CollectionObjectField;
import ar.edu.itba.it.dev.common.io.CompoundObjectField;
import ar.edu.itba.it.dev.common.io.InlineObjectField;
import ar.edu.itba.it.dev.common.io.ObjectDefinition;
import ar.edu.itba.it.dev.common.io.ObjectField;
import ar.edu.itba.it.dev.common.io.annotations.Required;
import ar.edu.itba.it.dev.common.jpa.domain.ReflectionUtils;
import ar.edu.itba.it.dev.common.web.Models;
import ar.edu.itba.it.dev.common.web.actions.AbstractResourceAJAXAction;
import ar.edu.itba.it.dev.common.web.components.Wicket;
import ar.edu.itba.it.dev.common.web.components.events.ajax.AjaxClickableEventAdapter;
import ar.edu.itba.it.dev.common.web.datatable.ActionColumn;
import ar.edu.itba.it.dev.common.web.datatable.columns.ColumnBuilder;
import ar.edu.itba.it.dev.common.web.images.Images;
import ar.edu.itba.it.dev.common.web.io.FormComponentLibrary.FormComponentProvider;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import com.google.inject.Inject;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class IOForm<T> extends FormComponentPanel<T> implements IFormVisitorParticipant {
	
	@Inject private FormComponentLibrary library;

	public IOForm(String id, IModel<T> model, ObjectDefinition def) {
		super(id, model);

		RepeatingView fields = new RepeatingView("fields");
		
		for (ObjectField field: def.getFields()) {
			if (!field.isHidden()) {
				if (field instanceof CompoundObjectField) {
					fields.add(new CompoundFieldFragment(fields.newChildId(), this, (CompoundObjectField) field));
				} else if (field instanceof CollectionObjectField) {
					fields.add(new CollectionFieldFragment(fields.newChildId(), this, model, (CollectionObjectField) field));
				} else if (field instanceof InlineObjectField){
					fields.add(new InlineFieldFragment(fields.newChildId(), this, (InlineObjectField) field));
				} else {
					throw new UnsupportedOperationException();
				}
			}
		}
		
		add(fields);
	}

	@Override
	protected void convertInput() {
		setConvertedInput(getModelObject());
	}
	
	private Form<?> getRootForm()
	{
		Form<?> form = findParent(Form.class);
		Form<?> parent = form;
		do
		{
			form = parent;
			parent = form.findParent(Form.class);
		}
		while (parent != null);

		return form;
	}

	
    @Override
	public boolean processChildren() {
        IFormSubmitter submitter = getRootForm().findSubmittingButton();
        if (submitter == null)
            return false;
        
        return submitter.getForm() == findParent(Form.class); // TODO: cache parent form
    }
	
	private class InlineFieldFragment extends Fragment {
		public InlineFieldFragment(String id, MarkupContainer markupProvider, InlineObjectField field) {
			super(id, "inlineField", markupProvider);
			
			add(new Label("key", getString(field.getKey())));
			add(createFormComponent(field));
		}
	}
	
	private class CompoundFieldFragment extends Fragment {
		public CompoundFieldFragment(String id, MarkupContainer markupProvider, CompoundObjectField field) {
			super(id, "compoundField", markupProvider);
			
			add(new IOForm("fields", Models.forAttribute(getModel(), field), new BaseIOViewer(field)));
		}
	}
	
	private class CollectionFieldFragment extends Fragment {
		
		IModel<?> newModel;
		
		public CollectionFieldFragment(String id, MarkupContainer markupProvider, IModel<?> base, CollectionObjectField field) {
			super(id, "collectionField", markupProvider);
			
			add(new Label("key", getString(field.getKey())));
			add(createTable(base, field));
			add(createNewItemContainer(base, field));
			setOutputMarkupId(true);
		}

		private WebMarkupContainer createNewItemContainer(final IModel<?> base, CollectionObjectField field) {
			final String property = field.getFieldName();
			final Class<?> fieldsClass = field.getFieldsClass();
			Form<Void> container = new Form<Void>("container");
			if (Serializable.class.isAssignableFrom(field.getFieldsClass())) {
				newModel = new Model<Serializable>();
			} else {
				newModel = new LoadableDetachableModel<Object>() {
					@Override
					protected Object load() {
						return ReflectionUtils.newInstance(fieldsClass);
					}
				};
			}
			
			FormComponentProvider provider = library.forClass(field.getFieldsClass()).or(new IOFormFormComponentProvider(field));
			container.add(provider.get("newField", field.getFieldsClass(), newModel));
			
			container.add(Wicket.submitLink("add", Images.ADD, new AjaxClickableEventAdapter() {
				@Override
				public void onClick(Component component, AjaxRequestTarget target) {
					((Collection)new PropertyModel(base, property).getObject()).add(newModel.getObject());
					target.add(CollectionFieldFragment.this);
				}
			}));

			container.setVisible(newModel != null);
			return container;
		}

		private DataTable<?> createTable(final IModel<?> base, CollectionObjectField field) {
			final String property = field.getFieldName();
			List<IColumn<?>> columns = Lists.newArrayList();
			for (ObjectField att: field.getFields()) {
				columns.add(ColumnBuilder.forType(null).withTitle(att.getKey()).showing(att.getFieldName()).build());
			}
			ActionColumn<?> actions = new ActionColumn();
			actions.addAction(new AbstractResourceAJAXAction("delete." + field.getKey(), Images.DELETE) {
				@Override
				public void onExecute(Component component, IModel data, AjaxRequestTarget target) {
					Object toRemove = data.getObject();
					Iterable<?> iterable = (Iterable<?>) (new PropertyModel(base, property).getObject());
					Iterator<?> iter = iterable.iterator();
					while(iter.hasNext()) {
						Object o = iter.next();
						if (o.equals(toRemove)) {
							iter.remove();
							target.add(CollectionFieldFragment.this);
							return ;
						}
					}
				}
			});
			columns.add(actions);
			DataTable table = new DataTable("table", columns, new IterableDataProvider(new PropertyModel<Iterable<?>>(base, field.getFieldName())), Integer.MAX_VALUE);			
			table.addTopToolbar(new HeadersToolbar(table, null));
			table.addBottomToolbar(new NoRecordsToolbar(table));
			return table;
		}
	}
	
	private FormComponentPanel<?> createFormComponent(InlineObjectField field) {
		FormComponentPanel<?> component = library.forClass(field.getClazz()).get().get("field", field.getClazz(), Models.forAttribute(getModel(), field));
		component.setLabel(new ResourceModel(field.getKey(), field.getKey()));
		component.add(new RequiredBehaviour(field.hasAnnotation(Required.class)));
		Optional<Size> sizeAnnot = field.getAnnotation(Size.class);
		if (field.getClazz().equals(String.class) && sizeAnnot.isPresent()) {
			Size size = sizeAnnot.get();
			if (size.min() > 0) {
				if (size.max() < Integer.MAX_VALUE) {
					component.add(StringValidator.lengthBetween(size.min(), size.max()));
				} else {
					component.add(StringValidator.minimumLength(size.min()));
				}
			} else if (size.max() < Integer.MAX_VALUE) {
				component.add(StringValidator.maximumLength(size.max()));
			}
		}
		return component;
	}
	
	// necessary cause FormComponent#setRequired is final
	private static class RequiredBehaviour extends Behavior {
		
		private boolean required;
		
		public RequiredBehaviour(boolean required) {
			this.required = required;
		}

		@Override
		public void onConfigure(Component component) {
			((FormComponent<?>)component).setRequired(required);
		}
	}
	
	private class IOFormFormComponentProvider implements FormComponentProvider {

		private ObjectDefinition def;
		
		public IOFormFormComponentProvider(ObjectDefinition def) {
			this.def = def;
		}

		@Override
		public FormComponentPanel<?> get(String id, Class<?> clazz, IModel<?> model) {
			return new IOForm(id, model, def);
		}
	}
}
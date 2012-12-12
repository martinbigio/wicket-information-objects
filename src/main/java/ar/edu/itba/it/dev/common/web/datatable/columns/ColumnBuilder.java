package ar.edu.itba.it.dev.common.web.datatable.columns;

import java.util.List;

import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.convert.IConverter;
import org.joda.time.LocalDate;

import ar.edu.itba.it.dev.common.jpa.LocalDateRange;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

@SuppressWarnings("rawtypes")
public class ColumnBuilder<T> {

	private Class<T> type;
	private Class<?> filterType;
	private String titleKey;
	private String property;
	private String sortProperty;
	private String filterProperty;
	private String css;
	private IConverter converter;
	private Class<? extends FormComponent> editionComponent;
	private List<CellDecorator<T>> cellDecorators = Lists.newArrayList();
	private ResourceReferenceResolver<T> referenceResolver;

	public static <T> ColumnBuilder<T> forType(Class<T> type) {
		return new ColumnBuilder<T>(type);
	}

	public ColumnBuilder(Class<T> type) {
		super();
		this.type = type;
	}

	public ColumnBuilder<T> withTitle(String key) {
		Preconditions.checkState(this.titleKey == null, "Title resource already specified!");
		this.titleKey = key;
		return this;
	}

	public ColumnBuilder<T> showing(String property) {
		Preconditions.checkState(this.property == null, "Value property already specified!");
		this.property = property;
		return this;
	}

	public ColumnBuilder<T> sortedBy(String property) {
		Preconditions.checkState(this.sortProperty == null, "a sort criteria has already been given!");
		this.sortProperty = property;
		return this;
	}

	public ColumnBuilder<T> sorted() {
		Preconditions.checkNotNull(this.property, "Can't sort without a value property");
		Preconditions.checkState(sortProperty == null, "a sort criteria has already been given!");
		this.sortProperty = property;
		return this;
	}

	public ColumnBuilder<T> filteredBy(String property) {
		Preconditions.checkState(this.filterProperty == null, "Filter property already specified!");
		Preconditions.checkState(editionComponent == null, "Cannot filter and edit rows simultaneously");
		this.filterProperty = property;
		return this;
	}

	public ColumnBuilder<T> filteredBy(Class<?> type, String property) {
		Preconditions.checkState(this.filterProperty == null, "Filter property already specified!");
		Preconditions.checkState(editionComponent == null, "Cannot filter and edit rows simultaneously");
		this.filterProperty = property;
		this.filterType = type;
		return this;
	}

	public ColumnBuilder<T> filtered() {
		Preconditions.checkNotNull(this.property, "Can't sort without a value property");
		Preconditions.checkState(this.filterProperty == null, "Filter property already specified!");
		Preconditions.checkState(editionComponent == null, "Cannot filter and edit rows simultaneously");
		this.filterProperty = property;
		return this;
	}

	public ColumnBuilder<T> filtered(Class<?> type) {
		Preconditions.checkNotNull(this.property, "Can't sort without a value property");
		Preconditions.checkState(this.filterProperty == null, "Filter property already specified!");
		Preconditions.checkState(editionComponent == null, "Cannot filter and edit rows simultaneously");
		this.filterProperty = property;
		this.filterType = type;
		return this;
	}

	public ColumnBuilder<T> styledWithClass(String css) {
		this.css = css;
		return this;
	}

	public ColumnBuilder<T> convertedBy(IConverter converter) {
		Preconditions.checkNotNull(converter, "You must specify a converter");
		this.converter = converter;
		return this;
	}

	public ColumnBuilder<T> decoratedBy(CellDecorator<T> cellDecorator) {
		this.cellDecorators.add(cellDecorator);
		return this;
	}
	
	public ColumnBuilder<T> imagesResolvedby(ResourceReferenceResolver<T> referenceResolver) {
		this.referenceResolver = referenceResolver;
		return this;
	}

	public ColumnBuilder<T> editedWith(Class<? extends FormComponent> component) {
		Preconditions.checkNotNull(component, "You must specify FormComponent for editing each row");
		Preconditions.checkState(filterProperty == null, "Cannot filter and edit rows simultaneously");
		this.editionComponent = component;
		return this;
	}

	public IColumn<T> build() {
		Preconditions.checkNotNull(titleKey, "Column must have a title resource");
		if (hasFilter()) {
			if (isBoolean()) {
				return new CheckBoxFilteredColumn<T>(this);
			} else if (isDate()) {
				return new DateFilteredColumn<T>(this);
			} else if (isDateRange()) {
				return new DateRangeFilteredColumn<T>(this);
			} else {
				return new TextFilteredColumn<T, Void>(this);
			}
		} else if(isPicture()) {
			return new PictureColumn<T>(this);
		} else {
			return new TextColumn<T>(this);
		}
	}

	public <V> IColumn<T> buildWithChoices(IModel<List<? extends V>> choices, boolean nullValid) {
		Preconditions.checkNotNull(titleKey, "Column must have a title resource");
		Preconditions.checkState(hasFilter(), "Choice columns must be filtered!");
		return new ChoiceFilteredColumn<T, V>(this, choices, nullValid);
	}
	
	private boolean hasFilter() {
		return filterProperty != null;
	}

	private boolean isEditable() {
		return editionComponent != null;
	}

	private boolean isBoolean() {
		return Boolean.class.equals(filterType);
	}

	private boolean isDate() {
		return LocalDate.class.equals(filterType);
	}

	private boolean isDateRange() {
		return LocalDateRange.class.equals(filterType);
	}
	
	private boolean isPicture() {
		return referenceResolver != null;
	}
	
	Class<T> getType() {
		return type;
	}

	String getTitleKey() {
		return titleKey;
	}

	String getProperty() {
		return property;
	}

	String getSortProperty() {
		return sortProperty;
	}

	String getFilterProperty() {
		return filterProperty;
	}

	String getCss() {
		return css;
	}

	public IConverter getCustomConverter() {
		return converter;
	}
	
	public List<CellDecorator<T>> getCellDecorators() {
		return cellDecorators;
	}
	
	public ResourceReferenceResolver<T> getResourceReferenceResolver() {
		return referenceResolver;
	}
	
}

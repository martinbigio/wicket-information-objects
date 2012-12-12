/*
 * Copyright (c) 2010 IT - ITBA -- All rights reserved
 */

package ar.edu.itba.it.dev.common.web.datatable.columns;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;

import ar.edu.itba.it.dev.common.web.TypedPanel;

/**
 * Convenience class for constructing PropertyColumns that shows a picture instead of a text.
 *
 * @param <T>
 */
public class PictureColumn<T> extends PropertyColumn<T> {
	
	private final ResourceReferenceResolver<T> referenceResolver;
	private String css;
	public PictureColumn(ColumnBuilder<T> builder) {
		super(new ResourceModel(builder.getTitleKey()), builder.getSortProperty(), builder.getProperty());
		this.referenceResolver = builder.getResourceReferenceResolver();
		this.css = builder.getCss();
	}	
	
	/**
	 * Override method to add an image instead of a {@link Label}
	 */
	
	@Override
	public void populateItem(Item<ICellPopulator<T>> item, String componentId,
			IModel<T> rowModel) {
		item.add(new PictureCellPanel(componentId, rowModel, referenceResolver));
	}
	
	@Override
	public String getCssClass() {
		return this.css;
	}
	
	private class PictureCellPanel extends TypedPanel<T> {

		private ResourceReferenceResolver<T> referenceResolver;

		public PictureCellPanel(String id, IModel<T> rowModel, ResourceReferenceResolver<T> referenceResolver) {
			super(id, rowModel);
			this.referenceResolver = referenceResolver;
		}
		
		@Override
		protected void onInitialize() {
			super.onInitialize();

			Image image = new Image("image", referenceResolver.resolveImage(getModelObject()));
			image.add(AttributeModifier.replace("title", new ResourceModel(referenceResolver.resolveText(getModelObject()))));
			image.add(AttributeModifier.replace("alt", new ResourceModel(referenceResolver.resolveAlt(getModelObject()))));
			add(image);
		};
	}
}

package ar.edu.itba.it.dev.common.web.components;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.extensions.ajax.markup.html.IndicatingAjaxButton;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.AbstractSubmitLink;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.link.AbstractLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.resource.ResourceReference;

import ar.edu.itba.it.dev.common.web.components.behaviors.ConfirmationBehavior;
import ar.edu.itba.it.dev.common.web.components.events.ClickableEventListener;
import ar.edu.itba.it.dev.common.web.components.events.EventAwareButton;
import ar.edu.itba.it.dev.common.web.components.events.EventAwareLink;
import ar.edu.itba.it.dev.common.web.components.events.EventAwareSubmitLink;
import ar.edu.itba.it.dev.common.web.components.events.FeedbackAjaxClickableEventListener;
import ar.edu.itba.it.dev.common.web.components.events.FeedbackClickableEventListener;
import ar.edu.itba.it.dev.common.web.components.events.ajax.AjaxClickableEventListener;
import ar.edu.itba.it.dev.common.web.components.events.ajax.EventAwareAjaxButton;
import ar.edu.itba.it.dev.common.web.components.events.ajax.EventAwareAjaxIndicatingButton;
import ar.edu.itba.it.dev.common.web.components.events.ajax.EventAwareAjaxIndicatingLink;
import ar.edu.itba.it.dev.common.web.components.events.ajax.EventAwareAjaxLink;
import ar.edu.itba.it.dev.common.web.components.events.ajax.EventAwareAjaxSubmitLink;
import ar.edu.itba.it.dev.common.web.images.Images;

/**
 * Static factory for wicket components as used by this project
 */
public class Wicket {
	private static final String BUTTON_RESOURCE = "Button.%s";

	/**
	 * Creates a new link component 
	 * @param id Identifier for the link
	 * @param model Model to use for the link
	 * @param listener Event listener that handles links
	 * @return A new link object
	 */
	public static <T> Link<T> link(String id, IModel<T> model, ClickableEventListener listener) {
		return new EventAwareLink<T>(id, model, new FeedbackClickableEventListener(listener));
	}
	
	/**
	 * Creates a new link component
	 * @param <T>
	 * @param id Identifier for the link
	 * @param listener Event listener that handles links
	 * @return A new link object
	 */
	public static <T> Link<T> link(String id, ClickableEventListener listener) {
		return new EventAwareLink<T>(id, new FeedbackClickableEventListener(listener));
	}
	
	public static <T> Link<T> confirmationLink(String id, String msgKey, ClickableEventListener listener) {
		Link<T> component =  new EventAwareLink<T>(id, new FeedbackClickableEventListener(listener));
		component.add(new ConfirmationBehavior(new ResourceModel(msgKey)));
		return component;
	}
	
	public static <T> Link<T> confirmationLink(String id, ResourceReference image, String confirmationResource, ClickableEventListener listener) {
		IModel<String> confirmationModel = new ResourceModel(confirmationResource);
		EventAwareLink<T> link = new EventAwareLink<T>(id, new FeedbackClickableEventListener(listener));
		link.add(new Image(id + "Image", image));
        link.add(AttributeModifier.replace("title", new ResourceModel(id + "Image" + ".title")));
        link.add(AttributeModifier.replace("alt", new ResourceModel(id + "Image" + ".alt")));
        link.add(new ConfirmationBehavior(confirmationModel));
		return link;
	}

	
	/**
	 * Creates a new link component with an image.
	 * @param <T>
	 * @param id Identifier for the link
	 * @param image that will be inserted in the link
	 * @param listener Event listener that handles links
	 * @return A new link object
	 */
	public static <T> Link<T> link(String id, ResourceReference image, ClickableEventListener listener) {
		return link(id, image, "", listener);
	}

	/**
	 * Creates a new link component with an image.
	 * @param id Identifier for the link
	 * @param image that will be inserted in the link
	 * @param resourceSuffix resource suffix for the alternative text
	 * @param listener Event listener that handles links
	 * @return A new link object
	 */
	public static <T> Link<T> link(String id, ResourceReference image, String resourceSuffix, ClickableEventListener listener) {
		EventAwareLink<T> link = new EventAwareLink<T>(id, new FeedbackClickableEventListener(listener));
		link.add(new Image(id + "Image", image));
        link.add(AttributeModifier.replace("title", new ResourceModel(id + "Image" + resourceSuffix + ".title")));
        link.add(AttributeModifier.replace("alt", new ResourceModel(id + "Image" + resourceSuffix + ".alt")));
		return link;
	}
	
	/**
	 * Creates a link with the delete icon and a confirmation behavior
	 * @param <T>
	 * @param id Identifier for the link
	 * @param confirmationResourceused for the confirmation message
	 * @param listener Event listener that handles links
	 * @return A new link object
	 */
	public static <T> Link<T> deleteLink(String id, String confirmationResource, ClickableEventListener listener) {
		return confirmationLink(id, Images.DELETE, confirmationResource, listener);
	}
	
	/**
	 * Creates a link with the delete icon and a confirmation behavior
	 * @param id Identifier for the link
	 * @param confirmationResourceused for the confirmation message
	 * @param listener Event listener that handles links
	 * @return A new link object
	 */
	public static AbstractLink deleteLink(String id, String confirmationResource, AjaxClickableEventListener listener) {
		IModel<String> confirmationModel = new ResourceModel(confirmationResource);
		AbstractLink link = new EventAwareAjaxLink<Void>(id, new FeedbackAjaxClickableEventListener(listener));
		link.add(new Image(id + "Image", Images.DELETE));
        link.add(AttributeModifier.replace("title", new ResourceModel(id + "Image" + ".title")));
        link.add(AttributeModifier.replace("alt", new ResourceModel(id + "Image" + ".alt")));
        link.add(new ConfirmationBehavior(confirmationModel));
		return link;
	}
	
	/**
	 * Creates a submit link with the delete icon and a confirmation behavior
	 * @param id Identifier for the link
	 * @param confirmationResourceused for the confirmation message
	 * @param listener Event listener that handles links
	 * @return A new link object
	 */
	public static AbstractSubmitLink deleteSubmitLink(String id, String confirmationResource, AjaxClickableEventListener listener) {
		return deleteSubmitLink(id, confirmationResource, null, listener);
	}
	
	
	/**
	 * Creates a submit link with the delete icon and a confirmation behavior
	 * @param id Identifier for the link
	 * @param confirmationResourceused for the confirmation message
	 * @param model to use as resource
	 * @param listener Event listener that handles links
	 * @return A new link object
	 */
	public static AbstractSubmitLink deleteSubmitLink(String id, String confirmationResource, IModel<?> model, AjaxClickableEventListener listener) {
		IModel<String> confirmationModel;
		if(model != null) {
			confirmationModel = new StringResourceModel(confirmationResource, model);
		} else {
			confirmationModel = new ResourceModel(confirmationResource);
		}	
		EventAwareAjaxSubmitLink link = new EventAwareAjaxSubmitLink(id, new FeedbackAjaxClickableEventListener(listener));
		link.add(new Image(id + "Image", Images.DELETE));
        link.add(AttributeModifier.replace("title", new ResourceModel(id + "Image" + ".title")));
        link.add(AttributeModifier.replace("alt", new ResourceModel(id + "Image" + ".alt")));
        link.add(new ConfirmationBehavior(confirmationModel));
		return link;
	}
	
	public static AbstractSubmitLink deleteSubmitLink(String id, String confirmationResource, IModel<?> model, ClickableEventListener listener) {
		IModel<String> confirmationModel;
		if(model != null) {
			confirmationModel = new StringResourceModel(confirmationResource, model);
		} else {
			confirmationModel = new ResourceModel(confirmationResource);
		}	
		EventAwareSubmitLink link = new EventAwareSubmitLink(id, listener);
		link.add(new Image(id + "Image", Images.DELETE));
        link.add(AttributeModifier.replace("title", new ResourceModel(id + "Image" + ".title")));
        link.add(AttributeModifier.replace("alt", new ResourceModel(id + "Image" + ".alt")));
        link.add(new ConfirmationBehavior(confirmationModel));
		return link;
	}

	
	/**
	 * Creates a submit link with the delete icon and a confirmation behavior
	 * @param id Identifier for the link
	 * @param confirmationResourceused for the confirmation message
	 * @param listener Event listener that handles links
	 * @return A new link object
	 */
	public static AbstractSubmitLink deleteSubmitLink(String id, String confirmationResource, ClickableEventListener listener) {
		return deleteSubmitLink(id, confirmationResource, null, listener);
	}
	
	/**
	 * Creates a new ajax button that submits a form
	 * @param id
	 * @param resource
	 * @param listener
	 * @return
	 */
	public static Button button(String id, String resource, AjaxClickableEventListener listener) {
		return new EventAwareAjaxButton(id, new ResourceModel(String.format(BUTTON_RESOURCE, resource)), new FeedbackAjaxClickableEventListener(listener));
	}
	
	/**
	 * Creates a new ajax button that submits a form with an indicating signal
	 * @param id
	 * @param resource
	 * @param listener
	 * @return
	 */
	public static IndicatingAjaxButton indicatingAjaxButton(String id, String resource, AjaxClickableEventListener listener) {
		return new EventAwareAjaxIndicatingButton(id, new ResourceModel(String.format(BUTTON_RESOURCE, resource)), new FeedbackAjaxClickableEventListener(listener));
	}
	
	public static Button confirmationButton(String id, String resource, AjaxClickableEventListener listener, String msgKey) {
		Button button = button(id, resource, listener);
		button.add(new ConfirmationBehavior(new ResourceModel(msgKey)));
		return button;
	}
	
	public static Button confirmationButton(String id, String resource, ClickableEventListener listener, String msgKey) {
		Button button = button(id, resource, listener);
		button.add(new ConfirmationBehavior(new ResourceModel(msgKey)));
		return button;
	}
	
	public static Button button(String id, String resource, ClickableEventListener listener) {
		return new EventAwareButton(id, new ResourceModel(String.format(BUTTON_RESOURCE, resource)), new FeedbackClickableEventListener(listener));
	}
	
	/**
	 * @param id
	 * @param resource
	 * @param listener
	 * @param visibilityListener
	 * @return
	 */
	public static Button cancelButton(String id, String resource, AjaxClickableEventListener listener) {
		return new EventAwareAjaxButton(id, new ResourceModel(String.format(BUTTON_RESOURCE, resource)), new FeedbackAjaxClickableEventListener(listener)).setDefaultFormProcessing(false);
	}
	
	/**
	 * @param id
	 * @param resource
	 * @param listener
	 * @param visibilityListener
	 * @return
	 */
	public static Button cancelButton(String id, String resource, ClickableEventListener listener) {
		return new EventAwareButton(id, new ResourceModel(String.format(BUTTON_RESOURCE, resource)), new FeedbackClickableEventListener(listener)).setDefaultFormProcessing(false);
	}

	
	/**
	 * Creates a new ajax link that contains an image and submits a form
	 * @param id
	 * @param image
	 * @param listener
	 * @return
	 */
	public static AbstractSubmitLink submitLink(String id, ResourceReference image, AjaxClickableEventListener listener) {
		AbstractSubmitLink link = new EventAwareAjaxSubmitLink(id, new FeedbackAjaxClickableEventListener(listener));
        link.add(new Image(id + "Image", image));
        link.add(AttributeModifier.replace("title", new ResourceModel(id + "Image" + ".title")));
        link.add(AttributeModifier.replace("alt", new ResourceModel(id + "Image" + ".alt")));
		return link;
	}
	
	public static AbstractSubmitLink submitLink(String id, ResourceReference image, ClickableEventListener listener) {
		AbstractSubmitLink link = new EventAwareSubmitLink(id, new FeedbackClickableEventListener(listener));
        link.add(new Image(id + "Image", image));
        link.add(AttributeModifier.replace("title", new ResourceModel(id + "Image" + ".title")));
        link.add(AttributeModifier.replace("alt", new ResourceModel(id + "Image" + ".alt")));
		return link;
	}


	public static AbstractSubmitLink submitLink(String id, ClickableEventListener listener) {
		AbstractSubmitLink link = new EventAwareSubmitLink(id, new FeedbackClickableEventListener(listener));
		return link;
	}

	/**
	 * Creates a new ajax link
	 * @param id
	 * @param image
	 * @param listener
	 * @return
	 */
	public static AbstractSubmitLink submitLink(String id, AjaxClickableEventListener listener) {
		return new EventAwareAjaxSubmitLink(id, new FeedbackAjaxClickableEventListener(listener));
	}


	/**
	 * @param id
	 * @param image
	 * @param listener
	 * @param visibilityEventListener
	 * @return
	 */
	public static AbstractLink link(String id, ResourceReference image, AjaxClickableEventListener listener) {
		AbstractLink link = new EventAwareAjaxLink<Void>(id, new FeedbackAjaxClickableEventListener(listener) );
        link.add(new Image(id + "Image", image));
        link.add(AttributeModifier.replace("title", new ResourceModel(id + "Image" + ".title")));
        link.add(AttributeModifier.replace("alt", new ResourceModel(id + "Image" + ".alt")));
		return link;
	}
	
	/**
	 * @param id
	 * @param listener
	 * @param visibilityEventListener
	 * @return
	 */
	public static AbstractLink link(String id, AjaxClickableEventListener listener) {
		return new EventAwareAjaxLink<Void>(id, new FeedbackAjaxClickableEventListener(listener) );
	}
	
	public static AbstractLink indicatingLink(String id, AjaxClickableEventListener listener) {
		return new EventAwareAjaxIndicatingLink<Void>(id, new FeedbackAjaxClickableEventListener(listener));
	}
	
	public static Label label(String id, IModel<?> model, String property) {
		return new Label(id, new PropertyModel<Object>(model, property));
	}
}

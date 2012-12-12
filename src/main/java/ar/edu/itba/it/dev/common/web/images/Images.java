package ar.edu.itba.it.dev.common.web.images;

import org.apache.wicket.request.resource.PackageResourceReference;
import org.apache.wicket.request.resource.ResourceReference;


/**
 * Resource references for standard images that can be used in an application
 * <p>
 * <b>NOTE:</b> Do not add image resources here if they are not generic actions. For 
 * project specific recourses create other class within it's module 
 * </p>
 */
public class Images {
	// Normal actions in a list
	public static final ResourceReference ADD = new PackageResourceReference(Images.class, "add.png");
	public static final ResourceReference DELETE = new PackageResourceReference(Images.class, "delete.png");
	public static final ResourceReference EDIT = new PackageResourceReference(Images.class, "edit.png");
	public static final ResourceReference DETAILS = new PackageResourceReference(Images.class, "details.png");

	// Normal actions will editing
	public static final ResourceReference ACCEPT = new PackageResourceReference(Images.class, "accept.png");
	public static final ResourceReference CANCEL = new PackageResourceReference(Images.class, "cancel.png");

	// Other actions 
	public static final ResourceReference COPY = new PackageResourceReference(Images.class, "copy.png");	
	public static final ResourceReference SAVE = new PackageResourceReference(Images.class, "save.png");
	public static final ResourceReference PRINT = new PackageResourceReference(Images.class, "printer.png");
	public static final ResourceReference COMMENTS = new PackageResourceReference(Images.class, "comments.png");
	public static final ResourceReference PLAY = new PackageResourceReference(Images.class, "play.png"); 

	// Toggle switches 
	public static final ResourceReference SWITCH_ON = new PackageResourceReference(Images.class, "changeStateUp.png");	
	public static final ResourceReference SWITCH_OFF = new PackageResourceReference(Images.class, "changeStateDown.png");	
	
	// Color dots used to indicate status 
	public static final ResourceReference GREEN_DOT = new PackageResourceReference(Images.class, "greendot.png");
	public static final ResourceReference RED_DOT = new PackageResourceReference(Images.class, "reddot.png");

	// Warning symbols 
	public static final ResourceReference WARNING = new PackageResourceReference(Images.class, "warning.png");

	// Representation of true and false as tick and cross
	public static final ResourceReference TRUE = new PackageResourceReference(Images.class, "true.png");
	public static final ResourceReference FALSE = new PackageResourceReference(Images.class, "false.png");

	// Representation of valid / invalid state
	public static final ResourceReference VALID = new PackageResourceReference(Images.class, "valid.png");
	public static final ResourceReference INVALID = new PackageResourceReference(Images.class, "invalid.png");
	
	// Advance indicator
	public static final ResourceReference IN_PROGRESS = new PackageResourceReference(Images.class, "inprogress.png");
	
	// Expanded - collapsed
	public static final ResourceReference PLUS = new PackageResourceReference(Images.class, "plus.png");
	public static final ResourceReference MINUS = new PackageResourceReference(Images.class, "minus.png");
	
	// Data correction mode on
	public static final ResourceReference KING = new PackageResourceReference(Images.class, "king.png");
	
}

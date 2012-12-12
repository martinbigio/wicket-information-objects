package ar.edu.itba.it.dev.common.web.components.behaviors;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.wicket.Component;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.transformer.AbstractTransformerBehavior;

/**
 * Behavior that converts URLs in component markup to html links
 */
public class LinkifyBehavior extends AbstractTransformerBehavior {
	private static final String urlPattern = "(?i)\\b((?:https?://|www\\d{0,3}[.]|[a-z0-9.\\-]+[.][a-z]{2,4}/)(?:[^\\s()<>]+|\\(([^\\s()<>]+|(\\([^\\s()<>]+\\)))*\\))+(?:\\(([^\\s()<>]+|(\\([^\\s()<>]+\\)))*\\)|[^\\s`!()\\[\\]{};:'\".,<>?«»“”‘’]))";
	private static final Pattern pattern = Pattern.compile(urlPattern);
	
	@Override
	public void onComponentTag(Component component, ComponentTag tag) {
		// this is needed to prevent wicket from adding a xml namespace attribute with a link that would be modified by this transformation
	}
	
	@Override
	public CharSequence transform(Component component, CharSequence output) throws Exception {
		String generateLinks = generateLinks(output);
		return generateLinks;
	}
	
	public static String generateLinks(CharSequence text) {  
	        Matcher matcher = pattern.matcher(text);  
	        return matcher.replaceAll("<a href=\"$1\">$1</a>");
	    }
	}

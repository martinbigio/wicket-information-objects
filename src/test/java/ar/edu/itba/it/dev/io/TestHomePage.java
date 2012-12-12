package ar.edu.itba.it.dev.io;

import org.apache.wicket.util.tester.WicketTester;
import org.junit.Before;
import org.junit.Test;

import ar.edu.itba.it.dev.io.web.EditStudentPage;
import ar.edu.itba.it.dev.io.web.WicketApplication;

/**
 * Simple test using the WicketTester
 */
public class TestHomePage
{
	private WicketTester tester;

	@Before
	public void setUp()
	{
		tester = new WicketTester(new WicketApplication());
	}

	@Test
	public void homepageRendersSuccessfully()
	{
		//start and render the test page
		tester.startPage(EditStudentPage.class);

		//assert rendered page class
		tester.assertRenderedPage(EditStudentPage.class);
	}
}

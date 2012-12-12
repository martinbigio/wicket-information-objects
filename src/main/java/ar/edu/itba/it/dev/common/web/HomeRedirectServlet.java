/*
 * Copyright (c) 2008 IT - ITBA -- All rights reserved
 */ 
package ar.edu.itba.it.dev.common.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;

@Singleton
public class HomeRedirectServlet extends HttpServlet  {
	private final String homeUrl;
	
	@Inject
	public HomeRedirectServlet(@Named("homeUrl") String homeUrl) {
		super();
		this.homeUrl = homeUrl;
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.sendRedirect(homeUrl);
	}
}

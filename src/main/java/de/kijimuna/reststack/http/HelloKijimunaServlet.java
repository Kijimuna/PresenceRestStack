package de.kijimuna.reststack.http;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.inject.Singleton;

@Singleton
public class HelloKijimunaServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	public void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException 
    {
	    response.setStatus(HttpServletResponse.SC_OK);
	    response.setContentType("text/html");
	    response.setCharacterEncoding("UTF-8");
	    response.getWriter().append("<H1>Hello \u30AD\u30B8\u30E0\u30CA\u30FC !</H1>");
	}

}

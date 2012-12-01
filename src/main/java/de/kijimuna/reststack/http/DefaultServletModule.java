package de.kijimuna.reststack.http;

import com.google.inject.Scopes;
import com.google.inject.servlet.ServletModule;

public class DefaultServletModule extends ServletModule{

	@Override
	protected void configureServlets() {

		bind(HelloKijimunaServlet.class).in(Scopes.SINGLETON);
        serve("/*").with(HelloKijimunaServlet.class);
	}

	
	
}

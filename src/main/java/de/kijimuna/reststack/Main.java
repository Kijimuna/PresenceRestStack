package de.kijimuna.reststack;

import java.util.EnumSet;

import org.eclipse.jetty.server.DispatcherType;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceFilter;

import de.kijimuna.reststack.http.DefaultServletModule;
import de.kijimuna.reststack.http.InvalidRequestServlet;
import de.kijimuna.reststack.presence.PresenceModule;
import de.kijimuna.reststack.rest.PresenceServletModule;


public class Main {

	public static void main(String[] args) throws Exception {
		Injector injector = createInjector();
		startJetty(injector);
	}

	private static Injector createInjector()
	{
		Injector injector = Guice.createInjector(new PresenceModule(), 
                                                 new PresenceServletModule(),  
				                                 new DefaultServletModule(),  
				                                 new AbstractModule() {
		    @Override
		    protected void configure() {
		        binder().requireExplicitBindings();
		        bind(GuiceFilter.class);
		    }
		});
		
		return injector;
	}

	private static void startJetty(Injector injector) throws Exception
	{
		Server server = new Server(8080);
		
		// we need a handler that invokes servlets
		ServletContextHandler handler = new ServletContextHandler();
		
		
		// OLD WAY: register a servlet explicitly
		// --------------------------------------
		// !ATTENTION! even if we go the guicy way, Jetty needs a servlet
		//             added for some reason. Since, in our case, the filter 
		//             handles "/*" as well, this servlet does not matter,
		//             though.
		handler.addServlet(new ServletHolder(new InvalidRequestServlet()), "/*");
		
		// GUICY WAY: register GuiceFilter
		// -------------------------------
		//            This way every servlet configured in a bound ServletModule  
		//            will be registered automatically.
		FilterHolder guiceFilter = new FilterHolder(injector.getInstance(GuiceFilter.class));
		handler.addFilter(guiceFilter, "/*", EnumSet.allOf(DispatcherType.class));

		handler.setContextPath("/");
		server.setHandler(handler);		
		server.start();
	}

}

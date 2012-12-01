package de.kijimuna.reststack.rest;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.google.inject.Scopes;
import com.google.inject.servlet.ServletModule;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;


public class PresenceServletModule extends ServletModule{

	@Override
	protected void configureServlets() {
        // bind our resources
        bind(PresenceResource.class);
        bind(SensorResource.class);
        
        // hook Jersey into Guice Servlet 
		// (GuiceContainer is a jersey class and contains the jersey servlet)
        bind(GuiceContainer.class);

        // hook Jackson into Jersey as the POJO <-> JSON mapper
        bind(JacksonJsonProvider.class).in(Scopes.SINGLETON);

        
        
        serve("/rest/*").with(GuiceContainer.class);
	}

	
	
}

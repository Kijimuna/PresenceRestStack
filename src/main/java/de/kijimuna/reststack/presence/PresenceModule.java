package de.kijimuna.reststack.presence;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

public class PresenceModule extends AbstractModule{

	@Override
	protected void configure() {
		bind(PresenceAggregator.class).in(Scopes.SINGLETON);
		bind(Sensors.class).in(Scopes.SINGLETON);
	}

}

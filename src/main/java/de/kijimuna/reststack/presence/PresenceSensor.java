package de.kijimuna.reststack.presence;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;


public abstract class PresenceSensor extends Thread
{
	private boolean shutdown = false;
	private Set<IPresenceConsumer> consumers = new CopyOnWriteArraySet<IPresenceConsumer>();
	private String name;
	


	public PresenceSensor(String name) {
		super(name + "-Thread");
		this.name = name;
	}

	public String getSensorName() {
		return name;
	}
	

	public boolean attachToPresenceConsumer(IPresenceConsumer consumer)
	{
		return consumers.add(consumer);
	}

	public boolean detachFromPresenceConsumer(IPresenceConsumer consumer)
	{
		return consumers.remove(consumer);
	}

	protected void presenceDetected()
	{
		for(IPresenceConsumer consumer : consumers)
		{
			consumer.presenceDetected(this);
		}
	}

	
	public void shutdown()
	{
		shutdown = true;
	}
	
	@Override
	public void run() {
		while(!shutdown)
		{
			aquireSensorData();
		}
	}

	
	protected abstract void aquireSensorData();
	
	
}

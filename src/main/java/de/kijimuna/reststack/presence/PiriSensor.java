package de.kijimuna.reststack.presence;

import java.util.Calendar;
import java.util.GregorianCalendar;


public class PiriSensor extends PresenceSensor
{

	private Calendar nextSimulatedPresence = new GregorianCalendar();
	
	public PiriSensor(String name) {
		super(name);
	}

	@Override
	protected void aquireSensorData() {
		// simulates presence every whole minute

		Calendar now = new GregorianCalendar();

		if(now.compareTo(nextSimulatedPresence) >= 0)
		{
			presenceDetected();
			
			nextSimulatedPresence  = now;
			nextSimulatedPresence.add(Calendar.MINUTE, 1);
			nextSimulatedPresence.set(Calendar.SECOND, 0);
			nextSimulatedPresence.set(Calendar.MILLISECOND, 0);

		}

		try {
			synchronized (this) {
				wait(nextSimulatedPresence.getTimeInMillis() - 
						 new GregorianCalendar().getTimeInMillis());
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	
	
}

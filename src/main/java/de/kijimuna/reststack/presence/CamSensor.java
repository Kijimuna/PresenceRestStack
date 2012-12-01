package de.kijimuna.reststack.presence;

import java.util.Calendar;
import java.util.GregorianCalendar;


public class CamSensor extends PresenceSensor
{

	private Calendar nextSimulatedPresence = new GregorianCalendar();
	
	public CamSensor(String name) {
		super(name);
	}

	@Override
	protected void aquireSensorData() {
		// simulates presence every two minutes at a half minute

		Calendar now = new GregorianCalendar();

		if(now.compareTo(nextSimulatedPresence) >= 0)
		{
			presenceDetected();
			
			nextSimulatedPresence  = now;
			nextSimulatedPresence.add(Calendar.MINUTE, 2);
			nextSimulatedPresence.set(Calendar.SECOND, 30);
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

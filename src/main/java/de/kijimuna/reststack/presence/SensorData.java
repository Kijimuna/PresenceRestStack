package de.kijimuna.reststack.presence;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class SensorData {

	private final Calendar attached = new GregorianCalendar();
	private Calendar lastPresenceDetected = null;
	
	public Calendar getAttachDate() {
		return attached;
	}

	public Calendar getlastPresenceDate() {
		return lastPresenceDetected;
	}

	public void setPresence() {
		lastPresenceDetected = new GregorianCalendar();
	}

}

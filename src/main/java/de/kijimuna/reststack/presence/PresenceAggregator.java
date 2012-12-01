package de.kijimuna.reststack.presence;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.concurrent.ThreadSafe;

import com.google.inject.Singleton;

@ThreadSafe
@Singleton
public class PresenceAggregator implements IPresenceConsumer {
	
	private HashMap<PresenceSensor, SensorData> sensorData = new HashMap<PresenceSensor, SensorData>();

	public synchronized boolean attachSensor(PresenceSensor sensor) {
		if(sensorData.containsKey(sensor))
			return false;

		sensorData.put(sensor, new SensorData());
		sensor.attachToPresenceConsumer(this);
		return true;
	}

	public synchronized boolean detachSensor(PresenceSensor sensor) {
		if(!sensorData.containsKey(sensor))
			return false;

		sensor.detachFromPresenceConsumer(this);
		sensorData.remove(sensor);
		return true;
	}

	public SensorData getData(PresenceSensor sensor) {
		return sensorData.get(sensor);
	}

	public Map<String, SensorData> getSnapshot() 
	{
		Map<String, SensorData> result = new HashMap<String, SensorData>();
		
		synchronized (this) {
			for(PresenceSensor sensor : sensorData.keySet())
				result.put(sensor.getSensorName(), sensorData.get(sensor));
		}
		
		return result;
	}

	
	//
	// IPrensence Consumer callback implementation
	//

	public void presenceDetected(PresenceSensor sensor) {
		SensorData data = sensorData.get(sensor);
		
		if(data!=null)
			data.setPresence();
	}

	
}

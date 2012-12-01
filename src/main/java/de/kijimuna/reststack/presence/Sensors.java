package de.kijimuna.reststack.presence;



import javax.annotation.concurrent.ThreadSafe;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.inject.Singleton;

@ThreadSafe
@Singleton
public class Sensors {
	
	// TODO still hardcoded :-(

	private static final ImmutableMap<String, PresenceSensor> SENSORS = 
			ImmutableMap.<String, PresenceSensor>builder()
				.put("PIRI1", new PiriSensor("PIRI1"))
				.put("PIRI2", new PiriSensor("PIRI2"))
				.put("PIRI3", new PiriSensor("PIRI3"))
				.put("PIRI4", new PiriSensor("PIRI4"))
				.put("PIRI5", new PiriSensor("PIRI5"))
				.put("CAM1", new PiriSensor("CAM1"))
				.put("CAM2", new PiriSensor("CAM2"))
				.put("CAM3", new PiriSensor("CAM3"))
				.put("CAM4", new PiriSensor("CAM4"))
				.put("CAM5", new PiriSensor("CAM5"))
				.build();
	
	public Sensors() {
		for(PresenceSensor sensor: SENSORS.values())
			sensor.start();
	}
	
	public ImmutableSet<String> getSensorNames(){
		return SENSORS.keySet();
	}

	public PresenceSensor getSensor(String name){
		return SENSORS.get(name);
	}

}


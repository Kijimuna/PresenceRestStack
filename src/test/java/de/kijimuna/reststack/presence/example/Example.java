package de.kijimuna.reststack.presence.example;

import java.util.Calendar;
import java.util.Map;

import de.kijimuna.reststack.presence.CamSensor;
import de.kijimuna.reststack.presence.PiriSensor;
import de.kijimuna.reststack.presence.PresenceAggregator;
import de.kijimuna.reststack.presence.SensorData;

public class Example {

	public static void main(String[] args) throws InterruptedException {
		PiriSensor piri1 = new PiriSensor("PIRI1");
		PiriSensor piri2 = new PiriSensor("PIRI2");
		piri2.start();
		
		CamSensor cam1 = new CamSensor("CAM 1");
		cam1.start();
		CamSensor cam2 = new CamSensor("CAM 2");
		cam2.start();
		
		
		PresenceAggregator aggregator = new PresenceAggregator();
		aggregator.attachSensor(piri1);
		aggregator.attachSensor(piri2);
		aggregator.attachSensor(cam1);
		aggregator.attachSensor(cam2);
		
		while(true)
		{
			Map <String, SensorData>  snapshot = aggregator.getSnapshot();
			for(String sensor: snapshot.keySet())
			{
				SensorData data = snapshot.get(sensor);
				Calendar attachedDate = data.getAttachDate();
				Calendar presenceDate = data.getlastPresenceDate();
				System.out.println(sensor + 
						           "|" + format(attachedDate) + 
						           "|" + format(presenceDate));
			}
			System.out.println();
			Thread.sleep(20000);
		}
		
	}
	
	private static String format(Calendar calendar)
	{
		if(calendar==null)
			return "---";
		
		return "" + calendar.getTimeInMillis();
	}
}

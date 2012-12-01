package de.kijimuna.reststack.presence.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import de.kijimuna.reststack.presence.PresenceAggregator;
import de.kijimuna.reststack.presence.PresenceSensor;
import de.kijimuna.reststack.presence.SensorData;

@RunWith(MockitoJUnitRunner.class)
public class PresenceAggregatorTest {

	private static final String SENSOR_NAME = "SENSOR1";
	
	PresenceAggregator aggregator;
	@Mock PresenceSensor sensor;
	
	@Before
	public void setup()
	{
		aggregator = new PresenceAggregator();
		when(sensor.getSensorName()).thenReturn(SENSOR_NAME);
		when(sensor.attachToPresenceConsumer(aggregator)).thenReturn(true);

	}

	@Test
	public void sensorCanBeAttached()
	{
		assertTrue(aggregator.attachSensor(sensor));
		verify(sensor, times(1)).attachToPresenceConsumer(aggregator);
	}
	
	@Test 
	public void sensorCantBeAttachedTwice()
	{
		aggregator.attachSensor(sensor);
		assertFalse(aggregator.attachSensor(sensor));
		verify(sensor, times(1)).attachToPresenceConsumer(aggregator);
	}

	@Test 
	public void unattachedSensorsCantBeDetached()
	{
		assertFalse(aggregator.detachSensor(sensor));
		verify(sensor, never()).detachFromPresenceConsumer(aggregator);
	}

	@Test 
	public void attachedsensorsCanBeDetached()
	{
		aggregator.attachSensor(sensor);
		assertTrue(aggregator.detachSensor(sensor));
		verify(sensor, times(1)).detachFromPresenceConsumer(aggregator);
	}

	@Test 
	public void detachedSensorsCantBeDetached()
	{
		aggregator.attachSensor(sensor);
		aggregator.detachSensor(sensor);
		assertFalse(aggregator.detachSensor(sensor));
		verify(sensor, times(1)).detachFromPresenceConsumer(aggregator);
	}


	@Test
	public void attachDateIsRecognized()
	{
		Calendar now = new GregorianCalendar();
		aggregator.attachSensor(sensor);
		
		Calendar attachDate = aggregator.getData(sensor).getAttachDate();
		assertNotNull(attachDate);
		assertTrue(isEqualOrShortlyAfter(attachDate, now));
	}

	@Test
	public void showsNoDefaultPresence()
	{
		aggregator.attachSensor(sensor);
		assertNull(aggregator.getData(sensor).getlastPresenceDate());
	}

	@Test
	public void showsSignaledPresence()
	{
		aggregator.attachSensor(sensor);

		Calendar now = new GregorianCalendar();
		aggregator.presenceDetected(sensor);
		
		Calendar detectedDate = aggregator.getData(sensor).getlastPresenceDate();
		assertNotNull(detectedDate);
		assertTrue(isEqualOrShortlyAfter(detectedDate, now));
	}

	@Test
	public void showsOnlyLastSignaledPresence()
	{
		aggregator.attachSensor(sensor);

		aggregator.presenceDetected(sensor);
		Calendar firstDetectedDate = aggregator.getData(sensor).getlastPresenceDate();

		try {Thread.sleep(100);} catch (InterruptedException e) {}
		
		aggregator.presenceDetected(sensor);
		Calendar sndDetectedDate = aggregator.getData(sensor).getlastPresenceDate();
		assertNotNull(sndDetectedDate);
		assertTrue(sndDetectedDate.after(firstDetectedDate));
	}

	@Test 
	public void snapshotContainsAttachedSensors()
	{
		aggregator.attachSensor(sensor);

		PresenceSensor anotherSensor = mock(PresenceSensor.class);
		when(anotherSensor.getSensorName()).thenReturn("SENSOR2");
		aggregator.attachSensor(anotherSensor);
			
		Map<String, SensorData> snapshot = aggregator.getSnapshot();
		assertEquals(2, snapshot.size());
		assertTrue(snapshot.containsKey(SENSOR_NAME));
		assertTrue(snapshot.containsKey("SENSOR2"));
		
	}

	@Test 
	public void snapshotContainsInitialData()
	{
		Calendar now = new GregorianCalendar();
		aggregator.attachSensor(sensor);
		
		
		SensorData dataFromSnapshot = aggregator.getSnapshot().get(SENSOR_NAME);

		Calendar attachDate = dataFromSnapshot.getAttachDate();
		assertNotNull(attachDate);
		assertTrue(isEqualOrShortlyAfter(attachDate, now));

		Calendar presenceDate = dataFromSnapshot.getlastPresenceDate();
		assertNull(presenceDate);
		
	}

	@Test 
	public void snapshotContainsPresenceData()
	{
		aggregator.attachSensor(sensor);

		Calendar now = new GregorianCalendar();
		aggregator.presenceDetected(sensor);		
		
		SensorData dataFromSnapshot = aggregator.getSnapshot().get(SENSOR_NAME);
		Calendar presenceDate = dataFromSnapshot.getlastPresenceDate();
		assertNotNull(presenceDate);
		assertTrue(isEqualOrShortlyAfter(presenceDate, now));
		
	}

	
	// --------------------------------------------------------------------
	
	private static boolean isEqualOrShortlyAfter(Calendar date, Calendar precedingDate)
	{
		if(date==null || precedingDate==null)
			return false;
				
		long diff = date.getTimeInMillis() - precedingDate.getTimeInMillis();
		return (diff >= 0 && diff < 10);
	}

}

package de.kijimuna.reststack.rest;

import java.util.Map;

import javax.annotation.concurrent.ThreadSafe;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.common.collect.ImmutableSet;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import de.kijimuna.reststack.presence.PresenceAggregator;
import de.kijimuna.reststack.presence.PresenceSensor;
import de.kijimuna.reststack.presence.SensorData;
import de.kijimuna.reststack.presence.Sensors;


@Singleton
@Path("/sensors")
@ThreadSafe
@Produces(MediaType.APPLICATION_JSON)
public class SensorResource {

    private final Sensors sensors;
    private final PresenceAggregator presenceAggregator;

    @Inject
    SensorResource(Sensors sensors, PresenceAggregator presenceAggregator) {
        this.sensors = sensors;
        this.presenceAggregator = presenceAggregator;
    }


    @PUT
    @Path("/attach")
    @Consumes(MediaType.APPLICATION_JSON)
    public Map<String, SensorData> attach(String sensorname) {
    	PresenceSensor sensor = sensors.getSensor(sensorname);
    	if(sensor==null)
    		throw new InvalidSensorWebAppException(sensorname);

    	presenceAggregator.attachSensor(sensor);
    	return presenceAggregator.getSnapshot();
    }

    @PUT
    @Path("/detach")
    @Consumes(MediaType.APPLICATION_JSON)
    public Map<String, SensorData> detach(String sensorname) {
    	PresenceSensor sensor = sensors.getSensor(sensorname);
    	if(sensor==null)
    		throw new InvalidSensorWebAppException(sensorname);

    	presenceAggregator.detachSensor(sensor);
    	return presenceAggregator.getSnapshot();
    }

    @GET
    public ImmutableSet<String> getSensorNames(){
    	
        return sensors.getSensorNames();
    }
}

package de.kijimuna.reststack.rest;

import java.util.Map;

import javax.annotation.concurrent.ThreadSafe;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;

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

    //TODO this changes state and shut rather be a post
    @Path("/attach")
    @GET
    public Map<String, SensorData> attach(@QueryParam("sensor") String sensorname) {
    	PresenceSensor sensor = sensors.getSensor(sensorname);
    	if(sensor==null)
    		throw new WebApplicationException(Status.BAD_REQUEST);
    	return presenceAggregator.getSnapshot();
    }

    //TODO this changes state and shut rather be a post
    @Path("/detach")
    @GET
    public Map<String, SensorData> detach(@QueryParam("sensor") String sensorname) {
    	PresenceSensor sensor = sensors.getSensor(sensorname);
    	if(sensor==null)
    		throw new WebApplicationException(Status.BAD_REQUEST);
    	
    	presenceAggregator.detachSensor(sensor);
    	return presenceAggregator.getSnapshot();
    }

    @GET
    public ImmutableSet<String> getSensorNames(){
    	
        return sensors.getSensorNames();
    }
}

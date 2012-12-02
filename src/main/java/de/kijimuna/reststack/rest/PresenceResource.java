package de.kijimuna.reststack.rest;

import java.util.Map;

import javax.annotation.concurrent.ThreadSafe;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import de.kijimuna.reststack.presence.PresenceAggregator;
import de.kijimuna.reststack.presence.PresenceSensor;
import de.kijimuna.reststack.presence.SensorData;
import de.kijimuna.reststack.presence.Sensors;

@Singleton
@Path("/presence")
@ThreadSafe
@Produces(MediaType.APPLICATION_JSON)
public class PresenceResource {

    private final PresenceAggregator presence;
    private final Sensors sensors;

    @Inject
    PresenceResource(PresenceAggregator presence, Sensors sensors) {
        this.presence = presence;
        this.sensors = sensors;
    }

    @GET
    @Path("/sensor")
    @Consumes(MediaType.APPLICATION_XML)
    public SensorData getPresenceHTML(@QueryParam("sensor") String sensorname) {
    	PresenceSensor sensor = sensors.getSensor(sensorname);
    	if(sensor==null)
    		throw new InvalidSensorWebAppException(sensorname);

    	return presence.getData(sensor);
    }

    @GET
    @Path("/sensor")
    @Consumes(MediaType.APPLICATION_JSON)
    public SensorData getPresenceJSON( String sensorname) {
    	PresenceSensor sensor = sensors.getSensor(sensorname);
    	if(sensor==null)
    		throw new InvalidSensorWebAppException(sensorname);

    	return presence.getData(sensor);
    }

    @GET
    public Map<String, SensorData> getPresence() {
        return presence.getSnapshot();
    }
}

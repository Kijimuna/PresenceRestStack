package de.kijimuna.reststack.rest;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

public class InvalidSensorWebAppException extends WebApplicationException
{

	private static final long serialVersionUID = 1L;

	public InvalidSensorWebAppException(String sensorname)
	{
		super(Response.status(Status.BAD_REQUEST)
				      .entity(sensorname + " is no valid sensor!")
				      .type(MediaType.APPLICATION_JSON)
				      .build());
	}
	
}

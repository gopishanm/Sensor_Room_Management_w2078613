package com.mycompany.clientservercw2;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.*;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SensorReadingResource {

    private static Map<Integer, List<Map<String, Object>>> readings = new HashMap<>();

    private int sensorId;

    public SensorReadingResource(int sensorId) {
        this.sensorId = sensorId;
    }

    
    @GET
    public List<Map<String, Object>> getReadings() {
        return readings.getOrDefault(sensorId, new ArrayList<>());
    }
    
Map<String, Object> sensor = SensorResource.sensors.get(sensorId);

if ("MAINTENANCE".equals(sensor.get("status"))) {
    throw new SensorUnavailableException("Sensor is under maintenance");
}

  @POST
public Response addReading(Map<String, Object> reading) {

    readings.putIfAbsent(sensorId, new ArrayList<>());
    readings.get(sensorId).add(reading);

    
    Map<String, Object> sensor = SensorResource.sensors.get(sensorId);

    if (sensor != null && reading.get("value") != null) {
        sensor.put("currentValue", reading.get("value"));
    }

    return Response.status(Response.Status.CREATED)
            .entity(reading)
            .build();
}
}
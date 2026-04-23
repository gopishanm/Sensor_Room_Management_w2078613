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

    @POST
    public Response addReading(Map<String, Object> reading) {

        // ? Check sensor exists
        Map<String, Object> sensor = SensorResource.sensors.get(sensorId);

        if (sensor == null) {
            throw new NotFoundException("Sensor not found");
        }

      
        if ("MAINTENANCE".equals(sensor.get("status"))) {
            throw new SensorUnavailableException("Sensor is under maintenance");
        }

        readings.putIfAbsent(sensorId, new ArrayList<>());
        readings.get(sensorId).add(reading);

      
        if (reading.get("value") != null) {
            sensor.put("currentValue", reading.get("value"));
        }

        return Response.status(Response.Status.CREATED)
                .entity(reading)
                .build();
    }
}
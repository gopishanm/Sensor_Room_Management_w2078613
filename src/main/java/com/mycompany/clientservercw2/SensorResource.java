package com.mycompany.clientservercw2;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Path("/sensors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SensorResource {

    public static Map<Integer, Map<String, Object>> sensors = new ConcurrentHashMap<>();
    private static int idCounter = 1;

    @POST
    public Response createSensor(Map<String, Object> sensor) {

        Object roomIdObj = sensor.get("roomId");

        if (roomIdObj == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("roomId is required")
                    .build();
        }

        int roomId = (int) roomIdObj;

        if (!SensorRoomResource.rooms.containsKey(roomId)) {
            throw new LinkedResourceNotFoundException("Room does not exist");
        }

        int id = idCounter++;
        sensor.put("id", id);
        sensors.put(id, sensor);

        // add sensor to room
        Map<String, Object> room = SensorRoomResource.rooms.get(roomId);

        List<Object> roomSensors = (List<Object>) room.get("sensors");

        if (roomSensors == null) {
            roomSensors = new ArrayList<>();
            room.put("sensors", roomSensors);
        }

        roomSensors.add(sensor);

        return Response.status(Response.Status.CREATED)
                .entity(sensor)
                .build();
    }

    
    @Path("/{sensorId}/readings")
    public SensorReadingResource getSensorReadings(@PathParam("sensorId") int sensorId) {

        Map<String, Object> sensor = sensors.get(sensorId);

        if (sensor == null) {
            throw new NotFoundException("Sensor not found");
        }

        return new SensorReadingResource(sensorId);
    }
}
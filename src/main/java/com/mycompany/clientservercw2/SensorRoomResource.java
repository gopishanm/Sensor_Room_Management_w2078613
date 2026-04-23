package com.mycompany.clientservercw2;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Path("/rooms")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SensorRoomResource {

    public static Map<Integer, Map<String, Object>> rooms = new ConcurrentHashMap<>();
    private static int idCounter = 1;

    @GET
    public Collection<Map<String, Object>> getAllRooms() {
        return rooms.values();
    }

    @POST
    public Response createRoom(Map<String, Object> room) {
        int id = idCounter++;
        room.put("id", id);

        // initialize sensors list
        room.put("sensors", new ArrayList<>());

        rooms.put(id, room);

        return Response.status(Response.Status.CREATED)
                .entity(room)
                .build();
    }

    @GET
    @Path("/{id}")
    public Response getRoom(@PathParam("id") int id) {
        Map<String, Object> room = rooms.get(id);

        if (room == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Room not found")
                    .build();
        }

        return Response.ok(room).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteRoom(@PathParam("id") int id) {

        Map<String, Object> room = rooms.get(id);

        if (room == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Room not found")
                    .build();
        }

        List<?> sensors = (List<?>) room.get("sensors");

        if (sensors != null && !sensors.isEmpty()) {
            // ? Assignment requirement: use 409
            throw new RoomNotEmptyException("Room has active sensors");
        }

        rooms.remove(id);

        return Response.ok("Room deleted successfully").build();
    }
}
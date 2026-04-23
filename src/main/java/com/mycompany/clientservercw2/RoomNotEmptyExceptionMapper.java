package com.mycompany.clientservercw2;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.HashMap;
import java.util.Map;

@Provider
public class RoomNotEmptyExceptionMapper implements ExceptionMapper<RoomNotEmptyException> {

    @Override
    public Response toResponse(RoomNotEmptyException ex) {

        Map<String, String> error = new HashMap<>();
        error.put("error", "Room is not empty");
        error.put("message", ex.getMessage());

        return Response.status(Response.Status.CONFLICT)
                .entity(error)
                .build();
    }
}
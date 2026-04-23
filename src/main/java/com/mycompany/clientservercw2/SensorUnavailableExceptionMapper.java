package com.mycompany.clientservercw2;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.HashMap;
import java.util.Map;

@Provider
public class SensorUnavailableExceptionMapper implements ExceptionMapper<SensorUnavailableException> {

    @Override
    public Response toResponse(SensorUnavailableException ex) {

        Map<String, String> error = new HashMap<>();
        error.put("error", "Sensor unavailable");
        error.put("message", ex.getMessage());

        return Response.status(Response.Status.FORBIDDEN) // 403
                .entity(error)
                .build();
    }
}
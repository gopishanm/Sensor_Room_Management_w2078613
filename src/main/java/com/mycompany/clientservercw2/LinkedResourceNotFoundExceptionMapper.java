package com.mycompany.clientservercw2;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.HashMap;
import java.util.Map;

@Provider
public class LinkedResourceNotFoundExceptionMapper implements ExceptionMapper<LinkedResourceNotFoundException> {

    @Override
    public Response toResponse(LinkedResourceNotFoundException ex) {

        Map<String, String> error = new HashMap<>();
        error.put("error", "Linked resource not found");
        error.put("message", ex.getMessage());

        return Response.status(422) 
                .entity(error)
                .build();
    }
}
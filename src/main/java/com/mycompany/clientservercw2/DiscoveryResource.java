package com.mycompany.clientservercw2;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.HashMap;
import java.util.Map;

@Path("/")
public class DiscoveryResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> getApiInfo() {

        Map<String, Object> response = new HashMap<>();


        response.put("version", "1.0");

        Map<String, String> contact = new HashMap<>();
        contact.put("name", "Your Name");
        contact.put("email", "your@email.com");

        response.put("contact", contact);

    
        Map<String, String> resources = new HashMap<>();
        resources.put("hello", "/api/v1/hello");

        response.put("resources", resources);

        return response;
    }
}
package com.mycompany.clientservercw2;

import jakarta.ws.rs.ApplicationPath;
import org.glassfish.jersey.server.ResourceConfig;

@ApplicationPath("/api/v1")
public class MyApplication extends ResourceConfig {
    public MyApplication() {
        packages("com.mycompany.clientservercw2");
    }
}
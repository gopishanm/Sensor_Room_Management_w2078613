package com.mycompany.clientservercw2;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;
import com.mycompany.clientservercw2.DiscoveryResource;

public class Main {

    public static void main(String[] args) throws Exception {

        Server server = new Server(8092);

        ServletContextHandler context =
                new ServletContextHandler(server, "/");

       
        ResourceConfig config = new ResourceConfig()
        .packages("com.mycompany.clientservercw2");
        config.register(ResourceHello.class);
        config.register(DiscoveryResource.class);
        config.register(SensorRoomResource.class); 

        ServletHolder jerseyServlet =
                new ServletHolder(new ServletContainer(config));

        context.addServlet(jerseyServlet, "/api/v1/*");

        server.start();

        System.out.println("Server started at http://localhost:8092/api/v1/");

        server.join();
    }
}
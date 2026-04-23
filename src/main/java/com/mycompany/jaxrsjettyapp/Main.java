package com.mycompany;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;

public class Main {

    public static void main(String[] args) throws Exception {

        Server server = new Server(8081);

        ServletContextHandler context =
                new ServletContextHandler(server, "/");

        ServletHolder jerseyServlet =
                context.addServlet(ServletContainer.class, "/api/v1/*");

        jerseyServlet.setInitParameter(
                "jersey.config.server.provider.packages",
                "com.mycompany"
        );

        server.start();
        server.join();
    }
}
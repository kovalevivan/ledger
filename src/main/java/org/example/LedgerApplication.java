package org.example;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.example.dao.AccountBalanceDao;
import org.example.resource.TransferResource;
import org.example.service.TransferService;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

public class LedgerApplication {

    public static void main(String[] args) throws Exception {
        start(8080);
    }

    public static void start(Integer port) throws Exception {
        ServletContextHandler handler = buildUsingResourceConfig();
        Server server = new Server(port);
        server.setHandler(handler);
        try {
            server.start();
            server.join();
        } finally {
            server.destroy();
        }
    }

    public static ServletContextHandler buildUsingResourceConfig() {
        ServletContextHandler handler = new ServletContextHandler(ServletContextHandler.NO_SESSIONS);
        handler.setContextPath("/");

        ResourceConfig resourceConfig = new ResourceConfig();
        resourceConfig.register(TransferResource.class);
        resourceConfig.register(new AbstractBinder() {
            @Override
            protected void configure() {
                bindAsContract(TransferService.class);
                bindAsContract(AccountBalanceDao.class);
                bindAsContract(ObjectMapper.class);
            }
        });
        handler.addServlet(new ServletHolder(new ServletContainer(resourceConfig)), "/api/*");
        return handler;
    }

}
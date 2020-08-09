package com.gameszaum.counter.web;

import com.gameszaum.counter.Main;
import com.gameszaum.counter.util.ClassGetter;
import com.gameszaum.counter.web.handler.WebHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URISyntaxException;

public class WebServer {

    private int port;
    private HttpServer server;

    public WebServer(int port) {
        this.port = port;
    }

    private void registerHandlers(HttpServer server) throws IOException, URISyntaxException {
        ClassGetter.getClassesForPackage(Main.getInstance(), "com.gameszaum.counter.web.handler.list").stream().filter(aClass -> WebHandler.class.isAssignableFrom(aClass) && aClass != WebHandler.class).forEach(aClass -> {
            try {
                WebHandler handler = (WebHandler) aClass.newInstance();

                server.createContext(handler.getPath(), handler);
                System.out.println("Handler registered: " + handler.getClass().getSimpleName());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void create() {
        try {
            server = HttpServer.create(new InetSocketAddress(port), 0);
            registerHandlers(server);

            server.setExecutor(null);
            server.start();
            System.out.println("[Counter-API] WebServer started in port: " + port);
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public void delete() {
        server.stop(0);
    }
}

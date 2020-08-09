package com.gameszaum.counter.web.handler.list;

import com.gameszaum.counter.Main;
import com.gameszaum.counter.manager.ServerManager;
import com.gameszaum.counter.server.Server;
import com.gameszaum.counter.web.handler.WebHandler;
import com.sun.net.httpserver.HttpExchange;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;

public class ServersHandler extends WebHandler {

    private ServerManager serverManager;

    public ServersHandler() {
        super("/servers");

        serverManager = Main.getInstance().getServerManager();
    }

    @Override
    public void handle(HttpExchange he) throws IOException {
        String serverName = he.getRequestURI().getRawPath().replaceAll("/servers/", "");

        if (!serverName.isEmpty() && !serverName.equals("/servers")) {
            Server server = serverManager.getServer(serverName);

            if (server != null) {
                try {
                    JSONObject body = new JSONObject(), items = new JSONObject();
                    JSONArray players = new JSONArray();

                    server.getPlayers().forEach(s -> players.add(s));

                    items.put("closed", server.isClosed());
                    items.put("online count", server.getOnlineCount());
                    items.put("players", players.toJSONString());

                    body.put(server.getName(), items);
                    changeTextBody(he, body.toJSONString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                changeTextBody(he, "server not found. 404", 404);
            }
            return;
        }
        JSONObject body = new JSONObject(), items = new JSONObject();

        serverManager.getServers().forEach(server -> {
            try {
                if (server.isClosed()) {
                    items.put(server.getName(), "closed");
                } else {
                    items.put(server.getName(), server.getOnlineCount() + " players");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        body.put("servers", items);
        changeTextBody(he, body.toJSONString());
    }
}

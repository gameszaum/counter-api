package com.gameszaum.counter.manager;

import com.gameszaum.counter.server.Server;
import net.md_5.bungee.api.ProxyServer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ServerManager {

    private Set<Server> servers;

    public ServerManager() {
        servers = new HashSet<>();
    }

    private void createServer(String name, boolean closed, int onlineCount) {
        servers.add(new Server(name).setClosed(closed).setOnlineCount(onlineCount).setPlayers(new ArrayList<>()));
    }

    public Server updateServer(String name, boolean closed, int onlineCount, List<String> players) {
        return getServer(name).setClosed(closed).setOnlineCount(onlineCount).setPlayers(players);
    }

    public int getAllCount() {
        return servers.stream().filter(server -> !server.isClosed()).mapToInt(Server::getOnlineCount).sum();
    }

    public Server getServer(String name) {
        return servers.stream().filter(server -> server.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    public void loadServers() {
        ProxyServer.getInstance().getServers().forEach((s, info) -> createServer(s, true, info.getPlayers().size()));
    }

    public Set<Server> getServers() {
        return servers;
    }
}

package com.gameszaum.counter.server;

import java.util.List;

public class Server {

    private String name;
    private List<String> players;
    private int onlineCount;
    private boolean closed;

    public Server(String name) {
        this.name = name;
    }

    public Server setOnlineCount(int onlineCount) {
        this.onlineCount = onlineCount;

        return this;
    }

    public Server setClosed(boolean closed) {
        this.closed = closed;

        return this;
    }

    public Server setPlayers(List<String> players) {
        this.players = players;

        return this;
    }

    public List<String> getPlayers() {
        return players;
    }

    public String getName() {
        return name;
    }

    public int getOnlineCount() {
        return onlineCount;
    }

    public boolean isClosed() {
        return closed;
    }
}

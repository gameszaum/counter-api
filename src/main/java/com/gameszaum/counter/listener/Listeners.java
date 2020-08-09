package com.gameszaum.counter.listener;

import com.gameszaum.counter.Main;
import com.gameszaum.counter.manager.ServerManager;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.event.ServerSwitchEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.stream.Collectors;

public class Listeners implements Listener {

    private ServerManager serverManager;

    public Listeners() {
        serverManager = Main.getInstance().getServerManager();
    }

    @EventHandler
    public void loginServer(PostLoginEvent event) {
        if (event.getPlayer() == null) return;
        if (event.getPlayer().getServer() == null || event.getPlayer().getServer().getInfo() == null) return;

        ServerInfo info = event.getPlayer().getServer().getInfo();

        info.ping((result, error) -> serverManager.updateServer(info.getName(), (error != null), info.getPlayers().size(), info.getPlayers().stream().map(ProxiedPlayer::getName).collect(Collectors.toList())));
    }

    @EventHandler
    public void switchServer(ServerSwitchEvent event) {
        if (event.getPlayer() == null) return;
        if (event.getPlayer().getServer() == null || event.getPlayer().getServer().getInfo() == null) return;

        ServerInfo info = event.getPlayer().getServer().getInfo();

        info.ping((result, error) -> serverManager.updateServer(info.getName(), (error != null), info.getPlayers().size(), info.getPlayers().stream().map(ProxiedPlayer::getName).collect(Collectors.toList())));
    }

}
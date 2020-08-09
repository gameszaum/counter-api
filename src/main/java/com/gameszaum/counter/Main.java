package com.gameszaum.counter;

import com.gameszaum.counter.listener.Listeners;
import com.gameszaum.counter.manager.ServerManager;
import com.gameszaum.counter.web.WebServer;
import net.md_5.bungee.api.plugin.Plugin;

public final class Main extends Plugin {

    private static Main instance;
    private ServerManager serverManager;
    private WebServer webServer;

    @Override
    public void onEnable() {
        instance = this;

        serverManager = new ServerManager();
        serverManager.loadServers();

        getProxy().getPluginManager().registerListener(this, new Listeners());

        webServer = new WebServer(9100);
        webServer.create();

        System.out.println("[Counter-API] API enabled, credits for gameszaum.");
    }

    @Override
    public void onDisable() {
        webServer.delete();
        webServer = null;
        instance = null;
        serverManager = null;

        getProxy().getPluginManager().unregisterListeners(this);

        System.out.println("[Counter-API] API disabled, credits for gameszaum.");
    }

    public static Main getInstance() {
        return instance;
    }

    public ServerManager getServerManager() {
        return serverManager;
    }
}
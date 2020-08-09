package com.gameszaum.counter.web.handler.list;

import com.gameszaum.counter.web.handler.WebHandler;
import com.sun.net.httpserver.HttpExchange;
import org.json.simple.JSONObject;

import java.io.IOException;

public class RootHandler extends WebHandler {

    public RootHandler() {
        super("/");
    }

    @Override
    public void handle(HttpExchange he) throws IOException {
        JSONObject json = new JSONObject();

        json.put("Counter-API's gameszaum", "1.0-SNAPSHOT version");

        changeTextBody(he, json.toJSONString());
    }
}

package com.gameszaum.counter.web.handler;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class WebHandler implements HttpHandler {

    private String path;

    public WebHandler(String path) {
        this.path = path;
    }

    public void changeTextBody(HttpExchange he, String text) throws IOException {
        he.sendResponseHeaders(200, text.length());
        OutputStream os = he.getResponseBody();
        os.write(text.getBytes());
        os.close();
    }

    public void changeTextBody(HttpExchange he, String text, int i) throws IOException {
        he.sendResponseHeaders(i, text.length());
        OutputStream os = he.getResponseBody();
        os.write(text.getBytes());
        os.close();
    }

    public void changeTextHeader(HttpExchange he, String text) throws IOException {
        Headers headers = he.getRequestHeaders();
        Set<Map.Entry<String, List<String>>> entries = headers.entrySet();

        for (Map.Entry<String, List<String>> entry : entries) {
            text += entry.toString() + "\n";
        }
        he.sendResponseHeaders(200, text.length());
        OutputStream os = he.getResponseBody();
        os.write(text.toString().getBytes());
        os.close();
    }

    public static void parseQuery(String query, Map<String,
            Object> parameters) throws UnsupportedEncodingException {

        if (query != null) {
            String pairs[] = query.split("[&]");
            for (String pair : pairs) {
                String param[] = pair.split("[=]");
                String key = null;
                String value = null;
                if (param.length > 0) {
                    key = URLDecoder.decode(param[0],
                            System.getProperty("file.encoding"));
                }

                if (param.length > 1) {
                    value = URLDecoder.decode(param[1],
                            System.getProperty("file.encoding"));
                }

                if (parameters.containsKey(key)) {
                    Object obj = parameters.get(key);
                    if (obj instanceof List<?>) {
                        List<String> values = (List<String>) obj;
                        values.add(value);

                    } else if (obj instanceof String) {
                        List<String> values = new ArrayList<String>();
                        values.add((String) obj);
                        values.add(value);
                        parameters.put(key, values);
                    }
                } else {
                    parameters.put(key, value);
                }
            }
        }
    }

    public String getPath() {
        return path;
    }

}

package com.mishchuk.httpserver;

import com.mishchuk.httpserver.config.Configuration;
import com.mishchuk.httpserver.config.ConfigurationManager;
import com.mishchuk.httpserver.core.ServerListenerThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * HttpServer Class
 */

public class HttpServer {
    public final static Logger LOGGER = LoggerFactory.getLogger(HttpServer.class);

    public static void main(String[] args) {

        LOGGER.info("Server starting...");

        ConfigurationManager.getInstance().loadConfigurationFile("src/main/resources/http.json");
        Configuration conf = ConfigurationManager.getInstance().getCurrentConfiguration();

        LOGGER.info(String.format("...Using Port:%s", conf.getPort()));
        LOGGER.info(String.format("...Using WebRoot:%s", conf.getWebroot()));

        try {
            ServerListenerThread serverListenerThread = new ServerListenerThread(conf.getPort(), conf.getWebroot());
            serverListenerThread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

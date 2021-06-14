package com.mishchuk.httpserver.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class HttpConnectionWorkerThread  extends Thread {
    public final static Logger LOGGER = LoggerFactory.getLogger(HttpConnectionWorkerThread.class);
    private Socket socket;


    public HttpConnectionWorkerThread(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run() {
        InputStream is = null;
        OutputStream os = null;
        try {

            LOGGER.info(String.format("...Connection is accepted %s", socket.getInetAddress()));
            is = socket.getInputStream();
            os = socket.getOutputStream();
            int _byte = is.read();
            while ((_byte=is.read())>=0){
                System.out.print((char)_byte);
            }
            String html = "<html><head><title>Simple Http Server</title></head><body><h1>Hello World</h1></body></html>";

            final String CRLF = "\n\r";

            String response =
                    "HTTP/1.1 200 OK" + CRLF +
                            "Content-Length: " + html.getBytes().length + CRLF +
                            CRLF +
                            html +
                            CRLF + CRLF ;
            os.write(response.getBytes());
            LOGGER.info("Connection Procesing Finished");
        } catch (IOException e) {
            LOGGER.error("Problen with communication", e);
        } finally {
            if (is!=null){
                try {
                    is.close();
                } catch (IOException e) {}
            }
            if (os!=null){
                try {
                    os.close();
                } catch (IOException e) {}
            }
            if (socket!=null){
                try {
                    socket.close();
                } catch (IOException e) {}
            }
        }
    }
}

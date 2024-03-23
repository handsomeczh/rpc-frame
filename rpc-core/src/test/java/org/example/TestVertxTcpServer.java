package org.example;

import com.czh.example.server.tcp.VertxTcpServer;

/**
 * @author czh
 * @version 1.0.0
 * 2024/3/22 15:04
 */
public class TestVertxTcpServer {
    public static void main(String[] args) {
        new VertxTcpServer().doStart(8888);
    }
}

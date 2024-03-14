package com.czh.example.server.impl;

import com.czh.example.server.HttpServer;
import io.vertx.core.Vertx;

/**
 * @author czh
 * @version 1.0.0
 * 2024/3/13 23:41
 */
public class VertxHttpService implements HttpServer {
    /**
     * 启动服务器
     * @param port
     */
    @Override
    public void doStart(int port) {
        Vertx vertx = Vertx.vertx();

        io.vertx.core.http.HttpServer server = vertx.createHttpServer();

//        监听端口并处理请求
        server.requestHandler(new HttpServerHandler());

//        启动 HTTP 服务器并监听指定的端口
        server.listen(port, result -> {
            if (result.succeeded()) {
                System.out.println("Server is now listen port "+port);
            } else {
                System.out.println("Failed to start server");
            }
        });
    }
}

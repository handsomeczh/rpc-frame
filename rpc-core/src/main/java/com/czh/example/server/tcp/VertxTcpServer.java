package com.czh.example.server.tcp;

import com.czh.example.server.HttpServer;
import io.vertx.core.Vertx;
import io.vertx.core.net.NetServer;
import lombok.extern.slf4j.Slf4j;

import java.lang.invoke.VarHandle;

/**
 * TCP 服务端实现
 *
 * @author czh
 * @version 1.0.0
 * 2024/3/21 21:14
 */
public class VertxTcpServer implements HttpServer {

    private byte[] handleRequest(byte[] requestData) {
        //实际逻辑需要根据具体的业务需求来实现
        return "你好，客户端，使用的是Vert.x的TCP服务".getBytes();
    }

    /**
     * 启动服务器
     */
    @Override
    public void doStart(int port) {
        //创建Vert.x 实例
        Vertx vertx = Vertx.vertx();

        //创建TCP服务器
        NetServer server = vertx.createNetServer();

        //处理请求
        server.connectHandler(new TcpServerHandler());

        server.listen(port, result -> {
            if (result.succeeded()) {
                System.out.println("TCP服务启动于端口: " + port);
            } else {
                System.out.println("无法启动TCP服务: " + result.cause());
            }
        });


    }
}

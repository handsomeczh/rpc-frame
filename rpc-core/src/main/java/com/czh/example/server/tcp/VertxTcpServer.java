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
@Slf4j
public class VertxTcpServer implements HttpServer {

    private byte[] handleRequest(byte[] requestData) {
        // todo 编写处理请求的逻辑，根据requestData构造响应数据并返回
        //实际逻辑需要根据具体的业务需求来实现
        return "Hello, client".getBytes();
    }

    /**
     * 启动服务器
     *
     * @param port
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
                System.out.println("TCP server started on port: " + port);
            } else {
                System.out.println("Failed to start TCP server: " + result.cause());
            }
        });


    }
}

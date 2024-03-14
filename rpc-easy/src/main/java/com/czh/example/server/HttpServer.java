package com.czh.example.server;

/**
 * @author czh
 * @version 1.0.0
 * 2024/3/13 23:39
 */

/**
 * HTTP 服务器接口
 * 定义统一的启动服务器接口，便于后续扩展，实现多种不同的web服务器
 */
public interface HttpServer {
    /**
     * 启动服务器
     *
     * @param port
     */
    void doStart(int port);
}

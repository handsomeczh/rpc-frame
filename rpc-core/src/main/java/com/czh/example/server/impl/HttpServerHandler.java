package com.czh.example.server.impl;

/**
 * @author czh
 * @version 1.0.0
 * 2024/3/14 11:47
 */

import com.czh.example.application.RpcApplication;
import com.czh.example.model.RpcRequest;
import com.czh.example.model.RpcResponse;
import com.czh.example.registry.LocalRegistry;
import com.czh.example.serializer.Serializer;
import com.czh.example.factory.SerializerFactory;
import com.czh.example.serializer.impl.JdkSerializer;
import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;

import java.io.IOException;
import java.lang.reflect.Method;

/**
 * HTTP 请求处理
 */
public class HttpServerHandler implements Handler<HttpServerRequest> {

    final Serializer serializer = SerializerFactory.getInstance(RpcApplication.getRpcConfig().getSerializer());

    //    反序列化请求为对象，并从请求对象中获取参数。
//    根据服务名称从本地注册器中获取到对应的服务实现类。
//    通过反射机制调用方法，得到返回结果。
//    对返回结果进行封装和序列化，并写入到响应中。
    @Override
    public void handle(HttpServerRequest request) {
//        指定序列化器
        final Serializer serializer = new JdkSerializer();
//        使用工厂+读取配置

//        记录日志
        System.out.println("RPC框架收到请求:" + request.method() + " " + request.path()+" : " + request.uri());

//        异步处理HTTP请求
        request.bodyHandler(body -> {
            byte[] bytes = body.getBytes();
            RpcRequest rpcRequest = null;
            try {
                rpcRequest = serializer.deserialize(bytes, RpcRequest.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
//        构造响应结果对象
            RpcResponse rpcResponse = new RpcResponse();
            if (rpcRequest == null) {
                rpcResponse.setMessage("服务消费者请求为空！！！");
                doResponse(request, rpcResponse, serializer);
                return;
            }

            try {
//              获取要调用的服务实现类，通过反射调用
                Class<?> implClass = LocalRegistry.get(rpcRequest.getServiceName());
                Method method = implClass.getMethod(rpcRequest.getMethodName(), rpcRequest.getParameterTypes());
                Object result = method.invoke(implClass.getDeclaredConstructor().newInstance(), rpcRequest.getArgs());
//                封装返回结果
                rpcResponse.setData(result);
                rpcResponse.setDataType(method.getReturnType());
                rpcResponse.setMessage("调用成功！！！");
            } catch (Exception e) {
                e.printStackTrace();
                rpcResponse.setMessage(e.getMessage());
                rpcResponse.setException(e);
            }
//            响应
            doResponse(request, rpcResponse, serializer);
        });
    }

    /**
     * 响应方法
     */
    private void doResponse(HttpServerRequest request, RpcResponse rpcResponse, Serializer serializer) {
        // application/octet-stream 相对与jdk数据
        HttpServerResponse httpServerResponse = request.response().putHeader("content-type", "application/json");
        try {
            byte[] serialize = serializer.serialize(rpcResponse);
            httpServerResponse.end(Buffer.buffer(serialize));
        } catch (IOException e) {
            e.printStackTrace();
            httpServerResponse.end(Buffer.buffer());
        }
    }
}

package com.czh.example.protocol;

import com.czh.example.factory.SerializerFactory;
import com.czh.example.model.RpcRequest;
import com.czh.example.model.RpcResponse;
import com.czh.example.serializer.Serializer;
import com.fasterxml.jackson.core.io.IOContext;
import io.vertx.core.buffer.Buffer;

import java.io.IOException;

import static com.czh.example.protocol.ProtocolConstant.MESSAGE_HEADER_LENGTH;

/**
 * 协议消息解码器
 * @author czh
 * @version 1.0.0
 * 2024/3/22 12:28
 */
public class ProtocolMessageDecoder {

    public static ProtocolMessage<?> decode(Buffer buffer) throws IOException{
        //分别从指定位置读出Buffer
        ProtocolMessage.Header header = new ProtocolMessage.Header();
        byte magic = buffer.getByte(0);
        if(magic != ProtocolConstant.PROTOCOL_MAGIC){
            throw new RuntimeException("消息 magic 非法");
        }
        header.setMagic(magic);
        header.setVersion(buffer.getByte(1));
        header.setSerializer(buffer.getByte(2));
        header.setType(buffer.getByte(3));
        header.setStatus(buffer.getByte(4));
        header.setRequestId(buffer.getByte(5));
        header.setBodyLength(buffer.getInt(13));
        //解决粘包问题，只读指定长度的数据
        byte[] bodyBytes = buffer.getBytes(MESSAGE_HEADER_LENGTH, MESSAGE_HEADER_LENGTH + header.getBodyLength());
        //解析消息体
        ProtocolMessageSerializerEnum serializerEnum = ProtocolMessageSerializerEnum.getEnumByKey(header.getSerializer());
        if(serializerEnum == null){
            throw new RuntimeException("序列化消息的协议不存在");
        }
        Serializer serializer = SerializerFactory.getInstance(serializerEnum.getValue());
        ProtocolMessageTypeEnum messageTypeEnum = ProtocolMessageTypeEnum.getEnumByKey(header.getType());
        if(messageTypeEnum == null){
            throw new RuntimeException("消息类型不存在");
        }
        switch (messageTypeEnum) {
            case REQUEST -> {
                RpcRequest request = serializer.deserialize(bodyBytes, RpcRequest.class);
                return new ProtocolMessage<>(header, request);
            }
            case RESPONSE -> {
                RpcResponse rpcResponse = serializer.deserialize(bodyBytes, RpcResponse.class);
                return new ProtocolMessage<>(header, rpcResponse);
            }
            default -> throw new RuntimeException("暂不支持该消息类型");
        }
    }
}

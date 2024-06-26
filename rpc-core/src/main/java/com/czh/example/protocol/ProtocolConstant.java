package com.czh.example.protocol;

/**
 * 协议常量
 *
 * @author czh
 * @version 1.0.0
 * 2024/3/21 20:35
 */
public class ProtocolConstant {

    /**
     * 消息头长度
     */
    public static int MESSAGE_HEADER_LENGTH = 17;

    /**
     * 协议魔数
     */
    public static byte PROTOCOL_MAGIC = 0x1;

    /**
     * 协议版本号
     */
    public static byte PROTOCOL_VERSION = 0x1;
}

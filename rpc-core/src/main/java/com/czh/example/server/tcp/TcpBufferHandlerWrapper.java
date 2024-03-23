package com.czh.example.server.tcp;

import com.czh.example.protocol.ProtocolConstant;
import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.parsetools.RecordParser;

/**
 * 装饰者模式（使用recordparser对原有的buffer处理能力进行增强）
 *
 * @author czh
 * @version 1.0.0
 * 2024/3/23 18:31
 */
public class TcpBufferHandlerWrapper implements Handler<Buffer> {

    /**
     * 解析器，用于解决半包、粘包问题
     */
    private final RecordParser recordParser;

    public TcpBufferHandlerWrapper(Handler<Buffer> bufferHandler) {
        recordParser = initRecordParser(bufferHandler);
    }

    @Override
    public void handle(Buffer buffer) {
        recordParser.handle(buffer);
    }

    private RecordParser initRecordParser(Handler<Buffer> bufferHandler) {
        //构造parser
        RecordParser parser = RecordParser.newFixed(ProtocolConstant.MESSAGE_HEADER_LENGTH);

        parser.setOutput(new Handler<Buffer>() {
            //初始化
            int size = -1;
            //一次完整的读取（头 + 体）
            Buffer resultBuffer = Buffer.buffer();

            @Override
            public void handle(Buffer buffer) {
                if (-1 == size) {
                    //读取消息长度
                    size = buffer.getInt(13);
                    parser.fixedSizeMode(size);
                    //写入头消息到结果
                    resultBuffer.appendBuffer(buffer);
                } else {
                    //写入体信息到结果
                    resultBuffer.appendBuffer(buffer);
                    bufferHandler.handle(resultBuffer);
                    //重置一轮
                    parser.fixedSizeMode(8);
                    size = -1;
                    resultBuffer = Buffer.buffer();
                }
            }
        });
        return parser;
    }

}

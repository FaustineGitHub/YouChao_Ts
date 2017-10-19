package com.gloiot.chatsdk.socket;

/**
 * 创建者 zengming.
 * 功能：回调集合类
 */
public class CallBackListener {

    /**
     * 接收消息后回调
     */
    public interface MessageListener {
        void messageContent(String data);
    }

    /**
     * 认证结果回调
     */
    public interface RenzhengResultListener {
        void renzhengResult(String result);
    }

    /**
     * 发送单聊消息结果回调
     */
    public interface SendSingleMessageListener {
        void sendResult(String result);
    }

}

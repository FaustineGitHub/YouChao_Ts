package com.gloiot.chatsdk.socket;

import io.socket.client.Ack;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

/**
 * 创建者 zengming.
 * 功能：Socket服务,专用操作Socket连接。断开连接
 */
public class SocketServer {

    private static Socket mSocket = LinkServer.getSocket();

    public SocketServer() {

    }

    /**
     * socket连接服务器
     */
    public static void socketOnConnect() {
        mSocket.on("connectionResult", SocketListener.getInstance().onConnect);
        mSocket.connect();
    }

    /**
     * 打开指定的socket连接
     * @param event 连接名
     * @param listener 回调事件
     */
    public static void socketOn(String event, Emitter.Listener listener) {
        mSocket.on(event, listener);
        mSocket.connect();
    }

    /**
     * 发送心跳消息
     * @param ack 回调事件
     */
    public static void socketHeartbeat(Ack ack) {
        mSocket.emit("time", "", ack);
    }

    /**
     * 关闭指定的socket连接
     * @param event 连接名
     * @param listener 回调事件
     */
    public static void socketOff(String event, Emitter.Listener listener) {
        mSocket.off(event, listener);
    }
    public static void socketOff(String event) {
        mSocket.off(event);
    }

    /**
     * 断开重连
     */
    public static void disReconnection() {
        mSocket.disconnect();
        socketOnConnect();
    }

    /**
     * 断开socket连接
     */
    public static void disconnect() {
        mSocket.disconnect();
    }

    /**
     * 返回socket连接状态
     */
    public static boolean connected() {
        return mSocket.connected();
    }
}

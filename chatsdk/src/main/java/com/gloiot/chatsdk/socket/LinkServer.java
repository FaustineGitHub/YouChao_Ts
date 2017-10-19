package com.gloiot.chatsdk.socket;

import android.content.Context;

import com.gloiot.chatsdk.network.HttpManager;
import com.gloiot.chatsdk.utils.Constant;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;

/**
 * 创建者 zengming.
 * 功能：链接通讯服务器
 */
public class LinkServer {

    private static Socket mSocket;

    /**
     * 连接通讯服务器
     */
    public static void init(Context context) {
        HttpManager.init(context, Constant.CHAT_SERVER_URL+"/api.post");

        try {
            mSocket = IO.socket(Constant.CHAT_SERVER_URL);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public static Socket getSocket() {
        return mSocket;
    }
}

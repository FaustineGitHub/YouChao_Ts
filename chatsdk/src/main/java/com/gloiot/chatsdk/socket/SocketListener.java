package com.gloiot.chatsdk.socket;

import android.text.TextUtils;
import android.util.Log;

import com.gloiot.chatsdk.network.HttpManager;
import com.gloiot.chatsdk.network.OnDataListener;
import com.gloiot.chatsdk.network.utils.EnDecryptUtlis;
import com.gloiot.chatsdk.network.utils.JsonUtils;
import com.gloiot.chatsdk.utils.Constant;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import io.socket.client.Ack;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

/**
 * 创建者 zengming.
 * 功能：Socket事件监听器
 */
public class SocketListener {

    private Socket mSocket = LinkServer.getSocket();
    public static SocketListener instance;
    private String renzhengid, userid, random;
    private static CallBackListener.MessageListener messageListener;
    private static CallBackListener.RenzhengResultListener renzhengResultListener;
    /**
     * 定时发送socket心跳，taskInt记录没有心跳的次数，3次后重新连接socket
     */
    private Timer timer = null;
    private TimerTask task = null;

    private SocketListener() {
    }

    /**
     * 初始化
     */
    public static SocketListener getInstance() {
        if (instance == null) {
            instance = new SocketListener();
        }
        return instance;
    }

    /**
     * 链接服务器的监听
     */
    public Emitter.Listener onConnect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            SocketServer.socketOff("message");
            Log.e("-----", "msg: " + args[0]);
            renzhengid = (String) args[0];

            heartbeatSocket();
            if (!TextUtils.isEmpty(userid) && !TextUtils.isEmpty(random)) {
                connectionRenZheng(userid, random);
            }
        }
    };

    /**
     * app重开后，将持久化存储的账号和随机码传入
     * @param userid 认证账号
     * @param random 认证随机码
     */
    public void staredData(final String userid, final String random) {
        this.userid = userid;
        this.random = random;
    }

    /**
     * 连接后进行认证，认证后可以接收指定消息
     * @param userid 认证账号
     * @param random 认证随机码
     */
    public void connectionRenZheng(final String userid, final String random) {
        this.userid = userid;
        this.random = random;

        HashMap<String, Object> hashMapInfo = new HashMap<>();
        hashMapInfo.put("userid", userid);
        hashMapInfo.put("random", random);
        hashMapInfo.put("socketuuid", renzhengid);

        Iterator ite = hashMapInfo.keySet().iterator();
        while (ite.hasNext()) {
            String key = (String) ite.next();
            Log.e("-hashMapInfo-", key + "==>" + hashMapInfo.get(key));
        }

        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("func", "auth");
        hashMap.put("words", random + EnDecryptUtlis.aesEncrypt(JsonUtils.createJSON(hashMapInfo).toString(), random));

        Iterator ite2 = hashMap.keySet().iterator();
        while (ite2.hasNext()) {
            String key = (String) ite2.next();
            Log.e("--hashMap--", key + "==>" + hashMap.get(key));
        }

        HttpManager.getRequestQueue().add(HttpManager.doPost(1, hashMap, new OnDataListener() {
            @Override
            public void onStart(int requestTag, int showLoad) {
                Log.e("----", "onStart: " + requestTag);
            }
            @Override
            public void onSuccess(int requestTag, JSONObject response, int showLoad) {
                Log.e("-----", "onSuccess: " + response);
                String msg = "";
                try {
                    msg = response.getString("msg");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                renzhengResultListener.renzhengResult(msg);
                if (Constant.RENZHENG_SUCCESS.equals(msg)) {
                    SocketServer.socketOn("message", new Emitter.Listener() {
                        @Override
                        public void call(Object... args) {
                            messageListener.messageContent(args[0].toString());
                            Ack ack = (Ack) args[args.length - 1];
                            String uuid = getUUID();
                            ack.call(uuid);
                        }
                    });
                    // 认证成功后发送一次心跳执行方法
                    SocketServer.socketHeartbeat(new Ack() {
                        @Override
                        public void call(Object... args) {
                            Log.e("---task---", args[0] + "");
                        }
                    });
                }

            }
            @Override
            public void onFailure(int requestTag, int showLoad) {
                Log.e("-----", "onFailure: ");
            }
            @Override
            public void onCancel(int requestTag, int showLoad) {

            }
        }, -1));
    }

    /**
     * 退出认证，并清除进程缓存的账号信息
     */
    public void signoutRenZheng() {
        this.userid = "";
        this.random = "";
        stopTimer();
        SocketServer.disReconnection();
    }

    /**
     * 心跳socket连接，定时器的创建和使用
     */
    public void heartbeatSocket() {
        stopTimer();
        if (timer == null) {
            timer = new Timer();
        }
        if (task == null) {
            task = new TimerTask() {
                @Override
                public void run() {
                    Log.e("---task---", SocketServer.connected() + "");// 判断socket是否在连接中
                    SocketServer.socketHeartbeat(new Ack() {
                        @Override
                        public void call(Object... args) {
                            Log.e("---task---", args[0] + "");
                        }
                    });
                }
            };
        }
        if(timer != null && task != null )
            timer.schedule(task, 0, 20000); // 打开定时器，20s执行一次
    }

    /**
     * 销毁定时器
     */
    private void stopTimer() {
        if (timer != null) {
            timer.cancel(); // 关闭定时器
            timer = null;
        }
        if (task != null) {
            task.cancel();
            task = null;
        }

    }

    /**
     * 随机生成一个不重复的uuid，用于接收消息后返回给后台
     * @return
     */
    public static String getUUID(){
        UUID uuid = UUID.randomUUID();
        String str = uuid.toString();
        return str;
    }

    public static void setMessageListener(CallBackListener.MessageListener messageListener) {
        SocketListener.messageListener = messageListener;
    }
    public static void setRenzhengResultListener(CallBackListener.RenzhengResultListener renzhengResultListener) {
        SocketListener.renzhengResultListener = renzhengResultListener;
    }

}

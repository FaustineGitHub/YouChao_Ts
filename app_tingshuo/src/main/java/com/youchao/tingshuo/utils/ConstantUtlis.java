package com.youchao.tingshuo.utils;

/**
 * Created by JinzLin on 2016/9/13.
 */

public class ConstantUtlis {

    // 微信Appid
    public static String WXAPPID = "wx8c55439fc255912a";

    //阿里云信息
//    public static final String endpoint = "http://oss-cn-shenzhen.aliyuncs.com";
//    public static final String accessKeyId = "ynjAwXSJsm6tHvbW";
//    public static final String accessKeySecret = "lnrN4oWq90GPEXZnmarHj8HOgfQFVe";
//    public static final String aliyunBucketName = "zykshop";
//    public static final String aliyunPath1 = "http://zykshop.qqjlb.cn/";

    //2017.06.17更改
    public static final String endpoint = "http://oss.glo-iot.com";
    public static final String accessKeyId = "LTAILX1dJyVDfG7W";
    public static final String accessKeySecret = "porFTxA7VyZOM2nlxHCJw47DaSF2jk";
    public static final String aliyunBucketName = "qqwlw";
    //class首的aliyunBucketName更改
    public static final String aliyunPath1 = "http://qqwlw.oss-cn-shenzhen.aliyuncs.com/";

    // SharedPreferences存储空间
    public static String MYSP = "yiyeshopinfo";
    // 手机ID
    public static String SP_PHENEID = "SP_PHENEID";
    // 手机名称
    public static String SP_PHENENAME = "SP_PHENENAME";
    // 用户随机码
    public static String SP_RANDOMCODE = "SP_RANDOMCODE";
    // 账号认证状态
    public static String SP_AUTHENTIFICATIONSTATE = "SP_AUTHENTIFICATIONSTATE";
    //账号登录状态
    public static String SP_LOGINSTATE = "SP_LOGINSTATE";
    //支付密码状态
    public static String SP_PAYPWDSTATE = "SP_PAYPWDSTATE";
    //用户账号
    public static String SP_USEACCOUNT = "SP_USEACCOUNT";
    //
    public static String SP_ISFROMLOGINTOMAIN = "SP_ISFROMLOGINTOMAIN";
    //来自通知消息
    public static String SP_FROMNOTIFICATION = "SP_FROMNOTIFICATION";

    // 数据库MESSAGE表
    public static String DB_MESSAGE = "DB_message";
    public static String DB_MESSAGE_MESSAGEID = "messageid";
    public static String DB_MESSAGE_SENDID = "sendid";
    public static String DB_MESSAGE_RECEIVEID = "receiveid";
    public static String DB_MESSAGE_MESSAGETYPE = "messagetype";
    public static String DB_MESSAGE_STATE = "state";
    public static String DB_MESSAGE_CONTENT = "content";
    public static String DB_MESSAGE_SENDTIME = "sendtime";
    public static String DB_MESSAGE_RECEIVEIDTIME = "receivetime";
    public static String DB_MESSAGE_REPLYID = "replyid";
    public static String DB_MESSAGE_REMARK = "remark";
    public static int DB_MESSAGE_MESSAGEID_TAG = 1;
    public static int DB_MESSAGE_SENDID_TAG = 2;
    public static int DB_MESSAGE_RECEIVEID_TAG = 3;
    public static int DB_MESSAGE_MESSAGETYPE_TAG = 4;
    public static int DB_MESSAGE_STATE_TAG = 5;
    public static int DB_MESSAGE_CONTENT_TAG = 6;
    public static int DB_MESSAGE_SENDTIME_TAG = 7;
    public static int DB_MESSAGE_RECEIVEIDTIME_TAG = 8;
    public static int DB_MESSAGE_REPLYID_TAG = 9;
    public static int DB_MESSAGE_REMARK_TAG = 10;

}

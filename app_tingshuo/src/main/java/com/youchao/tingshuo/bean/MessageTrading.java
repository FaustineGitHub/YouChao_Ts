package com.youchao.tingshuo.bean;

/**
 * Created by JinzLin on 2016/10/12.
 * 交易提醒
 */

public class MessageTrading {

    private String type;        //类型
    private String text;        //文字
    private String money;       //金额
    private String extra;       //备注
    private String jiaoyi;      //交易订单号

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public String getJiaoyi() {
        return jiaoyi;
    }

    public void setJiaoyi(String jiaoyi) {
        this.jiaoyi = jiaoyi;
    }
}

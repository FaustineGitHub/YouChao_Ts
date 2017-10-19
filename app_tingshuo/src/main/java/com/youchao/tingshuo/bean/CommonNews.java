package com.youchao.tingshuo.bean;

import java.util.ArrayList;

/**
 * Created by dell on 2017/9/22.
 */

public class CommonNews {
    private String title;
    private String source;
    private String time;
    private String url;
    private ArrayList<String> imgUrls;
    private int type;
    private String userIcon;
    private String zhuanfa;
    private String pinglun;
    private String dianzan;
    /**
     * 记录是否点赞了
     */
    private boolean isZan;

    public boolean isGuanZhu() {
        return isGuanZhu;
    }

    public void setGuanZhu(boolean guanZhu) {
        isGuanZhu = guanZhu;
    }

    /**
     * 记录是否关注了
     */
    private boolean isGuanZhu;

    public boolean isZan() {
        return isZan;
    }

    public void setZan(boolean zan) {
        isZan = zan;
    }






    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public CommonNews(String title, String source, String time, String url, ArrayList<String> imgUrls, int type, String userIcon, String zhuanfa, String pinglun, String dianzan, boolean isZan,boolean isGuanZhu) {
        this.title = title;
        this.source = source;
        this.time = time;
        this.url = url;
        this.imgUrls = imgUrls;
        this.type = type;
        this.userIcon = userIcon;
        this.zhuanfa = zhuanfa;
        this.pinglun = pinglun;
        this.dianzan = dianzan;
        this.isZan = isZan;
        this.isGuanZhu = isGuanZhu;
    }

    public ArrayList<String> getImgUrls() {
        return imgUrls;
    }

    public void setImgUrls(ArrayList<String> imgUrls) {
        this.imgUrls = imgUrls;
    }

    public String getUserIcon() {
        return userIcon;
    }

    public void setUserIcon(String userIcon) {
        this.userIcon = userIcon;
    }

    public String getZhuanfa() {
        return zhuanfa;
    }

    public void setZhuanfa(String zhuanfa) {
        this.zhuanfa = zhuanfa;
    }

    public String getPinglun() {
        return pinglun;
    }

    public void setPinglun(String pinglun) {
        this.pinglun = pinglun;
    }

    public String getDianzan() {
        return dianzan;
    }

    public void setDianzan(String dianzan) {
        this.dianzan = dianzan;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


}

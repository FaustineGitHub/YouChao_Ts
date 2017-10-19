package com.youchao.tingshuo.bean;

import java.util.ArrayList;

/**
 * Created by dell on 2017/9/25.
 * 圈子消息明细的基础类
 */

public class MessageCircle {
    private String usericon;
    private String username;
    private String time;
    private String imageinfo;
    private ArrayList<String> imgUrls;
    private int type;
    private String zhuanfa;
    private String pinglun;
    private String dianzan;
    private String deletepinglun;
    private String rightImage;
    private String rightVideo;
    private String rightText;

    public MessageCircle(String usericon, String username, String time, String imageinfo, ArrayList<String> imgUrls, int type, String zhuanfa, String pinglun, String dianzan, String deletepinglun, String rightVideo, String rightImage, String rightText) {
        this.usericon = usericon;
        this.username = username;
        this.time = time;
        this.imageinfo = imageinfo;
        this.imgUrls = imgUrls;
        this.type = type;
        this.zhuanfa = zhuanfa;
        this.pinglun = pinglun;
        this.dianzan = dianzan;
        this.deletepinglun = deletepinglun;
        this.rightImage = rightImage;
        this.rightVideo = rightVideo;
        this.rightText = rightText;
    }

    public String getRightImage() {
        return rightImage;
    }

    public void setRightImage(String rightImage) {
        this.rightImage = rightImage;
    }

    public String getRightVideo() {
        return rightVideo;
    }

    public void setRightVideo(String rightVideo) {
        this.rightVideo = rightVideo;
    }

    public String getRightText() {
        return rightText;
    }

    public void setRightText(String rightText) {
        this.rightText = rightText;
    }

    public String getUsericon() {
        return usericon;
    }

    public void setUsericon(String usericon) {
        this.usericon = usericon;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getImageinfo() {
        return imageinfo;
    }

    public void setImageinfo(String imageinfo) {
        this.imageinfo = imageinfo;
    }

    public ArrayList<String> getImgUrls() {
        return imgUrls;
    }

    public void setImgUrls(ArrayList<String> imgUrls) {
        this.imgUrls = imgUrls;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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

    public String getDeletepinglun() {
        return deletepinglun;
    }

    public void setDeletepinglun(String deletepinglun) {
        this.deletepinglun = deletepinglun;
    }
}

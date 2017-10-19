package com.youchao.tingshuo.bean;

/**
 * Created by dell on 2017/9/28.
 */

public class CommonPingLun {
    private String image_url;
    private String name;
    private String content;
    private String type;
    private String time;
    private String duixiang;

    public CommonPingLun(String image_url, String name, String content, String type, String time, String duixiang) {
        this.image_url = image_url;
        this.name = name;
        this.content = content;
        this.type = type;
        this.time = time;
        this.duixiang = duixiang;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDuixiang() {
        return duixiang;
    }

    public void setDuixiang(String duixiang) {
        this.duixiang = duixiang;
    }
}

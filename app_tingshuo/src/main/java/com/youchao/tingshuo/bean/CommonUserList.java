package com.youchao.tingshuo.bean;

/**
 * Created by dell on 2017/10/10.
 * 用户列表的数据
 */

public class CommonUserList {
    private String img;
    private String name;
    private String jieshao;
    private String type;

    public CommonUserList(String img, String name, String jieshao, String type) {
        this.img = img;
        this.name = name;
        this.jieshao = jieshao;
        this.type = type;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJieshao() {
        return jieshao;
    }

    public void setJieshao(String jieshao) {
        this.jieshao = jieshao;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

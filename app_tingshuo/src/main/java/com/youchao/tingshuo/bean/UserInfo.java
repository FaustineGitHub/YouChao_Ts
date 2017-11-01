package com.youchao.tingshuo.bean;

/**
 * Created by dell on 2017/10/31.
 * 用户资料
 */

public class UserInfo {
    private String nickname;
    private String sex;
    private String birthday;
    private String address;
    private String gexingqianming;

    public UserInfo() {
    }

    public UserInfo(String nickname, String sex, String birthday, String address, String gexingqianming) {
        this.nickname = nickname;
        this.sex = sex;
        this.birthday = birthday;
        this.address = address;
        this.gexingqianming = gexingqianming;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGexingqianming() {
        return gexingqianming;
    }

    public void setGexingqianming(String gexingqianming) {
        this.gexingqianming = gexingqianming;
    }
}

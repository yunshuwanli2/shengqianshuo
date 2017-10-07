package yswl.priv.com.shengqianshopping.bean;

import java.io.Serializable;

/**
 * 英雄榜
 */

public class HeroeListBean implements Serializable {

    private String avatar;

    private String nickname;

    private String integral;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getIntegral() {
        return integral;
    }

    public void setIntegral(String integral) {
        this.integral = integral;
    }
}

package yswl.priv.com.shengqianshopping.bean;

import java.io.Serializable;

/**
 * 第三方用户信息
 */

public class UserBean implements Serializable {
    public String uid;
    public String token;
    public String phoneStatus;
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPhoneStatus() {
        return phoneStatus;
    }

    public void setPhoneStatus(String phoneStatus) {
        this.phoneStatus = phoneStatus;
    }
}

package yswl.priv.com.shengqianshopping.bean;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

/**
 * 第三方用户信息
 */

public class UserBean implements Serializable {

    private String uid;
    private String nickname;
    private String avatar;
    private String phone;
    private String qq;
    private String email;
    private Alipay alipay;
    private Asset asset;

    public class Alipay {
        private String realName;
        private String aliAccount;

        public String getRealName() {
            return realName;
        }

        public void setRealName(String realName) {
            this.realName = realName;
        }

        public String getAliAccount() {
            return aliAccount;
        }

        public void setAliAccount(String aliAccount) {
            this.aliAccount = aliAccount;
        }
    }

    public class Asset {
        private String remainder;
        private String integral;
        private String revenue;

        public String getRemainder() {
            return remainder;
        }

        public void setRemainder(String remainder) {
            this.remainder = remainder;
        }

        public String getIntegral() {
            return integral;
        }

        public void setIntegral(String integral) {
            this.integral = integral;
        }

        public String getRevenue() {
            return revenue;
        }

        public void setRevenue(String revenue) {
            this.revenue = revenue;
        }
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Alipay getAlipay() {
        return alipay;
    }

    public void setAlipay(Alipay alipay) {
        this.alipay = alipay;
    }

    public Asset getAsset() {
        return asset;
    }

    public void setAsset(Asset asset) {
        this.asset = asset;
    }
}

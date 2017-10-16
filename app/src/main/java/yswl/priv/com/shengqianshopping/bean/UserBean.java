package yswl.priv.com.shengqianshopping.bean;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.Date;

import yswl.com.klibrary.util.DateJsonDeserializer;

/**
 * 第三方用户信息
 * <p>
 "uid": "18899992",
 "nickname": "tb18899992",
 "avatar": "https://wwc.alicdn.com/avatar/getAvatar.do?userId=784602545&width=160&height=160&type=sns",
 "phone": "15502192436",
 "qq": "",
 "email": "",
 "alipay": {
 "name": "kangpeng",
 "account": "yunshuwanli@foxmail.com"
 },
 "asset": {
 "remainder": 200,
 "integral": 0,
 "revenue": 20
 }
 * }
 */

public class UserBean implements Serializable {

    public String uid;
    public String nickname;
    public String avatar;
    public String phone;
    public String qq;
    public String email;
    public Alipay alipay;
    public Asset asset;
    public Invite invite;


    public class Invite{
        public String total;
        public String reward;
    }

    public class Alipay {
        public String name;
        public String account;

    }

    public class Asset {
        public String remainder;
        public String integral;
        public String revenue;

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

    public Invite getInvite() {
        return invite;
    }

    public void setInvite(Invite invite) {
        this.invite = invite;
    }

    public static UserBean jsonToBean(JSONObject json) {
        if (null == json) return null;

        try {
            Gson gson = new GsonBuilder().registerTypeAdapter(Date.class,
                    new DateJsonDeserializer()).create();
            Type data = new TypeToken<UserBean>() {
            }.getType();
            UserBean result = gson.fromJson(json.toString(), data);
            return result;
        } catch (Exception e) {
        }
        return null;
    }


}

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
 * <p>
 * "uid": "18892440",
 * "nickname": "tb18892440",
 * "avatar": "https%3A%2F%2Fwwc.alicdn.com%2Favatar%2FgetAvatar.",
 * "phone": "",
 * "qq": "",
 * "email": "",
 * "alipay": [],
 * "asset": {
 * "remainder": "0",
 * "integral": "0",
 * "revenue": "0"
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

    public class Alipay {
        public String realName;
        public String aliAccount;

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

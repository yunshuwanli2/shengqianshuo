package yswl.priv.com.shengqianshopping.event;

import yswl.priv.com.shengqianshopping.bean.UserBean;

/**
 * Created by kangpAdministrator on 2017/10/10 0010.
 * Emial kangpeng@yunhetong.net
 */

public class UserInfoEvent {
    public UserBean user;

    public UserInfoEvent(UserBean user) {
        this.user = user;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }
}

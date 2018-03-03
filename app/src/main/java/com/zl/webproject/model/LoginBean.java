package com.zl.webproject.model;

/**
 * Created by Administrator on 2018/3/2.
 */

public class LoginBean {

    private String phone;
    private String password;
    private boolean isLogin;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }
}

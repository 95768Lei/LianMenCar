package com.zl.webproject.model;

/**
 * Created by Administrator on 2018/2/27.
 */

public class BaseBean {

    private boolean result;
    private Object data;
    private String msg;

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}

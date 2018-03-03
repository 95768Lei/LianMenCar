package com.zl.webproject.model;

/**
 * Created by Administrator on 2018/3/3.
 */

public class TagEntity {

    /**
     * id : 131
     * dictName : 全款
     * pId : 130
     * dictKey : hotkey
     */

    private int id;
    private String dictName;
    private int pId;
    private String dictKey;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDictName() {
        return dictName;
    }

    public void setDictName(String dictName) {
        this.dictName = dictName;
    }

    public int getPId() {
        return pId;
    }

    public void setPId(int pId) {
        this.pId = pId;
    }

    public String getDictKey() {
        return dictKey;
    }

    public void setDictKey(String dictKey) {
        this.dictKey = dictKey;
    }
}

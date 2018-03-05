package com.zl.webproject.model;

import java.util.List;

/**
 * Created by Administrator on 2018/3/5.
 */

public class MoreTagBean {

    private String title;
    private List<TagBean> tagList;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<TagBean> getTagList() {
        return tagList;
    }

    public void setTagList(List<TagBean> tagList) {
        this.tagList = tagList;
    }
}

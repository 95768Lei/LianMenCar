package com.zl.webproject.model;

import java.util.List;

/**
 * Created by Administrator on 2018/2/27.
 */

public class PersonGridBean {
    private String title;
    private List<ImageList> imageList;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<ImageList> getImageList() {
        return imageList;
    }

    public void setImageList(List<ImageList> imageList) {
        this.imageList = imageList;
    }

    public static class ImageList {
        private Integer imagePath;
        private String name;

        public Integer getImagePath() {
            return imagePath;
        }

        public void setImagePath(Integer imagePath) {
            this.imagePath = imagePath;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

}

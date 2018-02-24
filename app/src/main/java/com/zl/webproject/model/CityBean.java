package com.zl.webproject.model;

/**
 * Created by Administrator on 2018/2/24.
 */

public class CityBean {

    private String name;
    private String cityCode;
    private String letters;

    public CityBean(String name) {
        this.name = name;
    }

    public CityBean() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getLetters() {
        return letters;
    }

    public void setLetters(String letters) {
        this.letters = letters;
    }

}

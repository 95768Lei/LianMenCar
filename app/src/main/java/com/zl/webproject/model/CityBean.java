package com.zl.webproject.model;

/**
 * Created by Administrator on 2018/2/24.
 */

public class CityBean {

    /**
     * id : 33
     * cityCode : 33
     * cityName : 嘉峪关市
     * citySort : J
     * cityShort : JYGS
     */

    private int id;
    private String cityCode;
    private String cityName;
    private String cityData;
    private String citySort;
    private String cityShort;
    private String letters;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCitySort() {
        return citySort;
    }

    public void setCitySort(String citySort) {
        this.citySort = citySort;
    }

    public String getCityShort() {
        return cityShort;
    }

    public void setCityShort(String cityShort) {
        this.cityShort = cityShort;
    }

    public String getLetters() {
        return letters;
    }

    public void setLetters(String letters) {
        this.letters = letters;
    }

    public String getCityData() {
        return cityData;
    }

    public void setCityData(String cityData) {
        this.cityData = cityData;
    }
}

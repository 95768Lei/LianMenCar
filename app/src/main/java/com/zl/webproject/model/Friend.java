package com.zl.webproject.model;

/**
 * Created by Administrator on 2018/2/24.
 */

public class Friend {

    private String name;
    private String letters;

    public Friend(String name) {
        this.name = name;
    }

    public Friend() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLetters() {
        return letters;
    }

    public void setLetters(String letters) {
        this.letters = letters;
    }

}

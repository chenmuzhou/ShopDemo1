package com.example.shopdemo.entiy;

/**
 * grid实体类
 */
public class MyGirdBean {
    private int imgId;
    private String iName;

    public MyGirdBean() {

    }

    public MyGirdBean(int imgId, String iName) {
        this.imgId = imgId;
        this.iName = iName;
    }

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

    public String getiName() {
        return iName;
    }

    public void setiName(String iName) {
        this.iName = iName;
    }
}

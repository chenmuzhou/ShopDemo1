package com.example.shopdemo.entiy;

/**
 * 个人中心我的订单grid实体类
 */
public class OrderGridBean  {
    private int imgId;
    private String iName;

    public OrderGridBean() {

    }

    public OrderGridBean(int imgId, String iName) {
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

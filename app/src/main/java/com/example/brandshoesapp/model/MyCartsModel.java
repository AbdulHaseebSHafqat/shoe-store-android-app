package com.example.brandshoesapp.model;

import java.io.Serializable;

public class MyCartsModel implements Serializable {
    String productName;
    String productPrice;
    String currentDate;
    String currentTime;
    String totalPrice;
    String totalQuantity;
    String documentId;
    String img_url;
    int imageRes = 0; // Added to support local images for New Arrivals

    public MyCartsModel() {
    }

    public MyCartsModel(String productName, String productPrice, String currentDate, String currentTime, String totalPrice, String totalQuantity, String documentId, String img_url, int imageRes) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.currentDate = currentDate;
        this.currentTime = currentTime;
        this.totalPrice = totalPrice;
        this.totalQuantity = totalQuantity;
        this.documentId = documentId;
        this.img_url = img_url;
        this.imageRes = imageRes;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    public String getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(String totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public int getImageRes() {
        return imageRes;
    }

    public void setImageRes(int imageRes) {
        this.imageRes = imageRes;
    }
}
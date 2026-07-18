package com.example.brandshoesapp.model;

public class DetailsOffersModel {
    int image;
    String name;
    String price;
    String discount;

    public DetailsOffersModel() {
    }

    public DetailsOffersModel(int image, String name, String price, String discount) {
        this.image = image;
        this.name = name;
        this.price = price;
        this.discount = discount;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }
}

package com.example.brandshoesapp.model;

import java.io.Serializable;

public class DetailsNewProductsModel implements Serializable {
    int image;
    String name;
    String description;
    String rating;
    String price;

    public DetailsNewProductsModel(int image, String name) {
        this.image = image;
        this.name = name;
        this.description = "Premium quality sneakers crafted for comfort and style.";
        this.rating = "4.5";
        this.price = "2500";
    }

    public DetailsNewProductsModel(int image, String name, String description, String rating, String price) {
        this.image = image;
        this.name = name;
        this.description = description;
        this.rating = rating;
        this.price = price;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}

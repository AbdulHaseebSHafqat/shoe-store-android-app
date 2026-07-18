package com.example.brandshoesapp.model;

public class NewProductsModel {
    int image;
    String name;
    String announcment;
    String type;


    public NewProductsModel() {
    }

    public NewProductsModel(int image, String name, String announcment, String type) {
        this.image = image;
        this.name = name;
        this.announcment = announcment;
        this.type = type;
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

    public String getAnnouncment() {
        return announcment;
    }

    public void setAnnouncment(String announcment) {
        this.announcment = announcment;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

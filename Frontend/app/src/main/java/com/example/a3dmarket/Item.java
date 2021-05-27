package com.example.a3dmarket;

public class Item {
    //here i can use String variable to store image URL

    private String img;
    private String fileUrl;
    private String name;
    private String price;
    private String description;

    public Item(String img, String fileUrl,String name, String price, String description) {
        this.img = img;
        this.fileUrl = fileUrl;
        this.name = name;
        this.price = price;
        this.description = description;
    }

    public Item(String img) {
        this.img = img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getImg() {
        return img;
    }
}

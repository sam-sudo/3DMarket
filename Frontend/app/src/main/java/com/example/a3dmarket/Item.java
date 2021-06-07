package com.example.a3dmarket;

import java.util.ArrayList;

public class Item {
    //here i can use String variable to store image URL

    private ArrayList<String> imgList;
    private String fileUrl;
    private String name;
    private String price;
    private String description;
    private String author;
    private String dateSale;

    @Override
    public String toString() {
        return "Item{" +
                "imgList=" + imgList +
                ", fileUrl='" + fileUrl + '\'' +
                ", name='" + name + '\'' +
                ", price='" + price + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public Item(ArrayList<String> imgList, String fileUrl, String name, String price, String description, String author) {
        this.imgList = imgList;
        this.fileUrl = fileUrl;
        this.name = name;
        this.price = price;
        this.description = description;
        this.author = author;
        this.dateSale = "";
    }

    /*public Item(String img) {
        this.imgList = img;
    }*/

    public void setImgList(ArrayList<String> img) {
        this.imgList = imgList;
    }

    public ArrayList<String> getImgList() {
        return imgList;
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
    public String getDateSale() {
        return dateSale;
    }

    public void setDateSale(String dateSale) {
        this.dateSale = dateSale;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}

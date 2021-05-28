package com.example.a3dmarket;

import java.util.ArrayList;

public class Item {
    //here i can use String variable to store image URL

    private ArrayList<String> imgList;
    private String fileUrl;
    private String name;
    private String price;
    private String description;

    public Item(ArrayList<String> imgList, String fileUrl,String name, String price, String description) {
        this.imgList = imgList;
        this.fileUrl = fileUrl;
        this.name = name;
        this.price = price;
        this.description = description;
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


}

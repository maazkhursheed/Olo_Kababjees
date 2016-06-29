package com.attribes.olo.kababjees.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maaz on 5/27/2016.
 */
//
public class MenusItem implements Serializable{

    private int id;
    private String name;
    private double price;
    private String description;
    private String created_at;
    private String updated_at;
    private int category_id;
    private List<Image> images = new ArrayList<Image>();
    private int desiredQuantity;

    public MenusItem(int id, String name, double price, String description, String created_at, String updated_at, int category_id, List<Image> images) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.category_id = category_id;
        this.images = images;
    }


    public MenusItem(int id, String name, double price, String description, String created_at, String updated_at, int category_id) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.category_id = category_id;
    }

    public MenusItem(int id, String name, int desiredQuantity) {
        this.id = id;
        this.name = name;
        this.desiredQuantity = desiredQuantity;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }


    public int getDesiredQuantity() {
        return desiredQuantity;
    }

    public void setDesiredQuantity(int desiredQuantity) {
        this.desiredQuantity = desiredQuantity;
    }


}

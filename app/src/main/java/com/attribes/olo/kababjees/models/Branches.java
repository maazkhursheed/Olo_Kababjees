package com.attribes.olo.kababjees.models;

/**
 * Created by Maaz on 8/25/2016.
 */
public class Branches {

    private Integer id;
    private String name;
    private String code;
    private String address;
    private Object phone;
    private Integer restaurantOwnerId;
    private String createdAt;
    private String updatedAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAddress() {
        return address;
    }


    public void setAddress(String address) {
        this.address = address;
    }

    public Object getPhone() {
        return phone;
    }


    public void setPhone(Object phone) {
        this.phone = phone;
    }

    public Integer getRestaurantOwnerId() {
        return restaurantOwnerId;
    }

    public void setRestaurantOwnerId(Integer restaurantOwnerId) {
        this.restaurantOwnerId = restaurantOwnerId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}

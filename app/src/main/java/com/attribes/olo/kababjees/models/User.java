package com.attribes.olo.kababjees.models;

/**
 * Created by attribe on 6/17/16.
 */
public class User {
    String name;
    String phone;
    String address;

    public User(String name, String phone, String address) {

        this.address = address;
        this.phone = phone;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}

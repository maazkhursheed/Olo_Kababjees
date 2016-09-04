package com.attribes.olo.kababjees.models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by attribe on 6/9/16.
 */
public class Orders implements Serializable {

    private String name;
    private String phone;
    private String address;
    private double order_total;
    private int order_time;
    private int device_os;
    private ArrayList<order_detail> order_detail;

    public Orders(String userName, String userPhone,
                  String userAddress, double ordertotal, int order_time, int device_os, ArrayList<order_detail> order_detail) {


        this.name=userName;
        this.phone=userPhone;
        this.address=userAddress;
        this.order_total =ordertotal;
        this.order_time = order_time;
        this.device_os = device_os;
        this.order_detail = order_detail;
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

    public double getOrder_total() {
        return order_total;
    }

    public void setOrder_total(double order_total) {
        this.order_total = order_total;
    }

    public int getOrder_time() {
        return order_time;
    }

    public void setOrder_time(int order_time) {
        this.order_time = order_time;
    }

    public int getDevice_os() {
        return device_os;
    }

    public void setDevice_os(int device_os) {
        this.device_os = device_os;
    }

    public ArrayList<com.attribes.olo.kababjees.models.order_detail> getOrder_detail() {
        return order_detail;
    }

    public void setOrder_detail(ArrayList<com.attribes.olo.kababjees.models.order_detail> order_detail) {
        this.order_detail = order_detail;
    }

    // private String deviceId;

}

package com.attribes.olo.kababjees.models;

import java.util.ArrayList;

/**
 * Created by attribe on 6/9/16.
 */
public class Orders {
   // private String deviceId;
    private String name;
    private String phone;
    private String address;
    private double order_total;
    private int order_time;
    private ArrayList<order_detail> order_detail;

    public Orders(String userName, String userPhone,
                  String userAddress, double ordertotal, int order_time, ArrayList<order_detail> order_detail) {


        this.name=userName;
        this.phone=userPhone;
        this.address=userAddress;
        this.order_total =ordertotal;
        this.order_time = order_time;
        this.order_detail = order_detail;
    }
}

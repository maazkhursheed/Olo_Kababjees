package com.attribes.olo.kababjees.models;

/**
 * Created by attribe on 6/9/16.
 */
public class order_detail {

    private int menu_id;
    private String item_name;
    private int quantity;
    private double item_price;

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public Integer getMenu_id() {
        return menu_id;
    }

    public void setMenu_id(Integer menu_id) {
        this.menu_id = menu_id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public double getItem_price() {
        return item_price;
    }

    public void setItem_price(double item_price) {
        this.item_price = item_price;
    }



    public order_detail(int menu_id, String item_name, int quantity, double item_price) {
        this.menu_id = menu_id;
        this.item_name = item_name;
        this.quantity = quantity;
        this.item_price = item_price;
    }



}

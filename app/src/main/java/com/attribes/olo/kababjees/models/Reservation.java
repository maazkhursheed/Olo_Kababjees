package com.attribes.olo.kababjees.models;

/**
 * Created by Maaz on 8/24/2016.
 */
public class Reservation {

    public Reservation(long time, int no_of_person, String phone, String name, int branch_id, String email) {
        this.time = time;
        this.no_of_person = no_of_person;
        this.phone = phone;
        this.name = name;
        this.branch_id = branch_id;
        this.email = email;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getNo_of_person() {
        return no_of_person;
    }

    public void setNo_of_person(int no_of_person) {
        this.no_of_person = no_of_person;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBranch_id() {
        return branch_id;
    }

    public void setBranch_id(int branch_id) {
        this.branch_id = branch_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long time;
    public int no_of_person;
    public String phone;
    public String name;
    public int branch_id;
    public String email;

}



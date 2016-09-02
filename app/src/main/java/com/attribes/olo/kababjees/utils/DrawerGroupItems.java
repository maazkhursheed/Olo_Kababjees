package com.attribes.olo.kababjees.utils;

import com.attribes.olo.kababjees.models.Category;

import java.util.ArrayList;

/**
 * Created by Maaz on 8/31/2016.
 */
public class DrawerGroupItems {

    String groupName;
    ArrayList<String> groupChild= new ArrayList<>();
    ArrayList<Category> categoriesList;

    public DrawerGroupItems(String groupName, ArrayList<String> stringChild ,ArrayList<Category> categoriesList) {
        this.groupName = groupName;
        this.groupChild = stringChild;
        this.categoriesList = categoriesList;
    }

    public ArrayList<Category> getCategoriesList() {
        return categoriesList;
    }

    public void setCategoriesList(ArrayList<Category> categoriesList) {
        this.categoriesList = categoriesList;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public ArrayList<String> getGroupChild() {
        return groupChild;
    }

    public void setGroupChild(ArrayList<String> groupChild) {
        this.groupChild = groupChild;
    }
}

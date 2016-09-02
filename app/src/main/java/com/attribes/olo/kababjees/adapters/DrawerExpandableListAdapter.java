package com.attribes.olo.kababjees.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.attribes.olo.kababjees.Screens.ModeSelection;
import com.attribes.olo.kababjees.models.NavDrawerItem;
import com.attribes.olo.kababjees.R;
import com.attribes.olo.kababjees.utils.DrawerGroupItems;


import java.util.ArrayList;

/**
 * Created by Maaz on 5/25/2016.
 */
public class DrawerExpandableListAdapter extends BaseExpandableListAdapter {

    public Activity activity;
    ArrayList<DrawerGroupItems> drawerGroupItemsList;
    public LayoutInflater inflater;

    public DrawerExpandableListAdapter(Activity act, ArrayList<DrawerGroupItems> drawerGroupItemsList) {
        activity = act;
        this.drawerGroupItemsList = drawerGroupItemsList;
        inflater = act.getLayoutInflater();
    }


    @Override
    public int getGroupCount() {
        return drawerGroupItemsList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return drawerGroupItemsList.get(groupPosition).getGroupChild().size();
    }

    @Override
    public DrawerGroupItems getGroup(int groupPosition) {
        return drawerGroupItemsList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {

        if(getGroup(groupPosition).getCategoriesList()==null){

            //case of menu group

            return getGroup(groupPosition).getGroupChild().get(childPosition);
        }

        else{
            //case of normal string items
            return getGroup(groupPosition).getCategoriesList().get(childPosition);
        }

    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.drawer_group_item, null);
        }
        ((CheckedTextView) convertView).setText(drawerGroupItemsList.get(groupPosition).getGroupName());
        ((CheckedTextView) convertView).setChecked(isExpanded);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        if(getChild(groupPosition,childPosition) instanceof String){


            //case where normal string is coming
            //implement functionality of normal string headers of menu here
        }


        else{


            //this is a category object
            //extract category id and pass it on click listener
        }
        TextView textChild = null;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.drawer_group_child_item, null);
        }
        textChild = (TextView) convertView.findViewById(R.id.groupChild);
        textChild.setText(drawerGroupItemsList.get(groupPosition).getGroupChild().get(childPosition));

        getChild(groupPosition,childPosition);
        return convertView;
    }

    @Override
    public void onGroupCollapsed(int groupPosition) {
        super.onGroupCollapsed(groupPosition);
    }

    @Override
    public void onGroupExpanded(int groupPosition) {
        super.onGroupExpanded(groupPosition);
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

}

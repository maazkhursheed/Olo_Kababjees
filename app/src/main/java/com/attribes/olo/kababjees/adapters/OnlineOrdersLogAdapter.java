package com.attribes.olo.kababjees.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckedTextView;
import android.widget.TextView;
import com.attribes.olo.kababjees.R;
import com.attribes.olo.kababjees.models.Orders;
import com.attribes.olo.kababjees.models.order_detail;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Maaz on 9/4/2016.
 */
public class OnlineOrdersLogAdapter extends BaseExpandableListAdapter {

    ArrayList<Orders> ordersList;
    public LayoutInflater inflater;
    public Activity activity;

    public OnlineOrdersLogAdapter(Activity act, ArrayList<Orders> ordersList) {

        activity = act;
        this.ordersList = ordersList;
        inflater = act.getLayoutInflater();
    }

    @Override
    public int getGroupCount() {
        return ordersList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return ordersList.get(groupPosition).getOrder_detail().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return ordersList.get(groupPosition).getOrder_time();
    }

    @Override
    public order_detail getChild(int groupPosition, int childPosition) {
        return ordersList.get(groupPosition).getOrder_detail().get(childPosition);
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
            convertView = inflater.inflate(R.layout.orderlog_group, null);
        }

        ((CheckedTextView) convertView).setText("Order Total: " + ordersList.get(groupPosition).getOrder_total()+ "\n" + "Time: " + unixToSimpleDateTime(ordersList.get(groupPosition).getOrder_time()) );
        ((CheckedTextView) convertView).setChecked(isExpanded);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final order_detail itemsOfOrder = getChild(groupPosition, childPosition);
        TextView text = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.orderlog_item, null);
        }
        text = (TextView) convertView.findViewById(R.id.orderLogChild);
        text.setText("Item Name: "+itemsOfOrder.getItem_name()+"    "+" Qty: "+itemsOfOrder.getQuantity());

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    public String unixToSimpleDateTime(Long obtainedUnixDateTime){

        Timestamp timeStamp = new Timestamp(obtainedUnixDateTime);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");
        String formattedTimeStamp = sdf.format(timeStamp);
        return formattedTimeStamp;                                             //String.valueOf(formattedTimeStamp);
    }
}

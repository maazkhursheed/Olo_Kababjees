package com.attribes.olo.kababjees.fragments;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ListView;
import com.attribes.olo.kababjees.R;
import com.attribes.olo.kababjees.adapters.OnlineOrdersLogAdapter;
import com.attribes.olo.kababjees.models.Orders;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Maaz on 9/3/2016.
 */
public class OnlineOrdersViewFragment extends Fragment {

    private View view;
    private ExpandableListView expandableOrdersList;
    private FrameLayout no_orderLayout;
    OnlineOrdersLogAdapter onlineOrdersAdapter;
    ArrayList<Orders> obtainedOrdersList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view =inflater.inflate(R.layout.fragment_online_orders_list, container, false);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        intViews(view);
        prepareOrdersList();


        return view;

    }

    private void intViews(View view) {

        expandableOrdersList = (ExpandableListView) view.findViewById(R.id.expOrderLogList);
        no_orderLayout = (FrameLayout) view.findViewById(R.id.noOrderLayout);
    }

    private void prepareOrdersList() {

        SharedPreferences mPrefs = getActivity().getPreferences(MODE_PRIVATE);
        obtainedOrdersList = new ArrayList<>();
        Gson gson = new Gson();
        String jsonOrders = mPrefs.getString("OnlineOrdersList",null);

        if(jsonOrders != null){
            Type type = new TypeToken<ArrayList<Orders>>(){}.getType();
            ArrayList<Orders> ordersObtainedList = gson.fromJson(jsonOrders, type);
            obtainedOrdersList = ordersObtainedList;
        }

        if(obtainedOrdersList.isEmpty() ){
            expandableOrdersList.setVisibility(View.GONE);
            no_orderLayout.setVisibility(View.VISIBLE);
        }
        else {

            onlineOrdersAdapter = new OnlineOrdersLogAdapter(getActivity(), obtainedOrdersList);
            expandableOrdersList.setAdapter(onlineOrdersAdapter);
//            makeListExpanded();
        }
    }


    private void makeListExpanded() {
        int count = onlineOrdersAdapter.getGroupCount();
        for (int position = 1; position <= count; position++) {
            expandableOrdersList.expandGroup(position - 1);
        }
    }

}

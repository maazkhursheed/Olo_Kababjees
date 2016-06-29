package com.attribes.olo.kababjees.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.attribes.olo.kababjees.Interfaces.OnItemRemoveListener;
import com.attribes.olo.kababjees.Interfaces.OnQuantityChangeListener;
import com.attribes.olo.kababjees.adapters.CartEditListAdapter;
import com.attribes.olo.kababjees.cart.ItemCart;
import com.attribes.olo.kababjees.R;

/**
 * Created by Maaz on 6/6/2016.
 */
public class OrderCheckEditListFragment extends Fragment {

    private RecyclerView recyclerView;
    private CartEditListAdapter cartEditListAdapter;
    private OnItemRemoveListener onItemRemoveListener;
    private OnQuantityChangeListener onQuantityChangeListener;

    public OrderCheckEditListFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_edit_cart_list, container, false);
        recyclerView = (RecyclerView)rootView.findViewById(R.id.editlistCart);
        prepareCartEditList();
        return rootView ;
    }

    public void prepareCartEditList(){

        cartEditListAdapter = new CartEditListAdapter(ItemCart.getOrderableItems());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(cartEditListAdapter);
    }

}

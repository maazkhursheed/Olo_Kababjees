package com.attribes.olo.kababjees.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.attribes.olo.kababjees.adapters.CartListAdapter;
import com.attribes.olo.kababjees.cart.ItemCart;
import com.attribes.olo.kababjees.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderCheckSimpleListFragment extends Fragment {

    private RecyclerView recyclerView;
    private CartListAdapter cartListAdapter;

    public OrderCheckSimpleListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_simple_cart_list, container, false);
        recyclerView = (RecyclerView)rootView.findViewById(R.id.simplelistCart);
        prepareCartSimpleList();
        return rootView ;
    }

    public void prepareCartSimpleList(){

        cartListAdapter = new CartListAdapter(ItemCart.getOrderableItems());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(cartListAdapter);
    }

}

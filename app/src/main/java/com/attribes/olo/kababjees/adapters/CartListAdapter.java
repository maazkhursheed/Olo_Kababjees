package com.attribes.olo.kababjees.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.attribes.olo.kababjees.models.MenusItem;
import com.attribes.olo.kababjees.R;

import java.util.List;

/**
 * Created by Maaz on 6/7/2016.
 */
public class CartListAdapter extends RecyclerView.Adapter<CartListAdapter.MyViewHolder> {

    private List<MenusItem> menusList;

    public CartListAdapter(List<MenusItem> menusList) {
        this.menusList = menusList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder  {

        public TextView itemQuantity, itemName, itemPrice;

        public MyViewHolder(View view) {
            super(view);

            itemQuantity = (TextView) view.findViewById(R.id.simpleItemQuantity);
            itemName = (TextView) view.findViewById(R.id.simpleItemName);
            itemPrice = (TextView) view.findViewById(R.id.simpleItemCost);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_check_simple_list, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        MenusItem menus = menusList.get(position);
        holder.itemQuantity.setText("" + menus.getDesiredQuantity());
        holder.itemName.setText("" + menus.getName());
        holder.itemPrice.setText("" + CartEditListAdapter.getItemPrice(menus.getDesiredQuantity(),menus.getPrice()));
    }

    @Override
    public int getItemCount() {
        return menusList.size();
    }
}

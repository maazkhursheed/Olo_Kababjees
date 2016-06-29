package com.attribes.olo.kababjees.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.attribes.olo.kababjees.Screens.MainActivity;
import com.attribes.olo.kababjees.cart.ItemCart;
import com.attribes.olo.kababjees.models.MenusItem;
import com.attribes.olo.kababjees.R;

import java.util.List;

/**
 * Created by Maaz on 6/7/2016.
 */
public class CartEditListAdapter extends RecyclerView.Adapter<CartEditListAdapter.MyViewHolder> {

    private List<MenusItem> menusList;
    private MenusItem menus;
    Context mContext;

    public CartEditListAdapter(List<MenusItem> menusList ) {
        this.menusList = menusList;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder  {

      final   public EditText  itemEditQuantity;
        public TextView  itemEditName, itemEditPrice, itemEditCross;

        public MyViewHolder(View view) {
            super(view);

            itemEditQuantity = (EditText) view.findViewById(R.id.editItemQuantity);
            itemEditName = (TextView) view.findViewById(R.id.ediItemName);
            itemEditPrice = (TextView) view.findViewById(R.id.editItemCost);
            itemEditCross = (TextView) view.findViewById(R.id.editItemCross);
        }
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_check_edit_list, parent, false);
        mContext = parent.getContext();
        return new CartEditListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        menus = menusList.get(position);
        holder.itemEditName.setText("" + menus.getName());
        holder.itemEditPrice.setText("" + getItemPrice(menus.getDesiredQuantity(),menus.getPrice()));
        holder.itemEditQuantity.setText("" + menus.getDesiredQuantity());    // EditionSetListner(position)
        holder.itemEditQuantity.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_DONE){

                    if( holder.itemEditQuantity.getText().toString().matches("0")){
                       removeItem(position);
                    }
                   else {
                        ItemCart.getOrderableItems().get(position).setDesiredQuantity(Integer.parseInt(String.valueOf(v.getText())));
                        holder.itemEditQuantity.setCursorVisible(false);
                        holder.itemEditQuantity.setSelection(holder.itemEditQuantity.getText().length());
                    }
                }

                return false;
            }
        });

        holder.itemEditCross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MainActivity.onItemRemoveListener.onItemRemoved((int) ItemCart.getInstance().getTotal());
                ItemCart.getOrderableItems().remove(position);
                Toast.makeText(mContext,"Item Removed",Toast.LENGTH_SHORT).show();
                notifyDataSetChanged();
            }
        });
    }

    private void removeItem(int position) {
        MainActivity.onItemRemoveListener.onItemRemoved((int) ItemCart.getInstance().getTotal());
        ItemCart.getOrderableItems().remove(position);
        Toast.makeText(mContext,"Item Removed",Toast.LENGTH_SHORT).show();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return menusList.size();
    }

    public static double getItemPrice(int itemQuantity, double itemPrice ) {

        double totalPrice = itemQuantity*itemPrice;
        return totalPrice;
    }

}

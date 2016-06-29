package com.attribes.olo.kababjees.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.attribes.olo.kababjees.models.MenusItem;
import com.attribes.olo.kababjees.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Maaz on 5/25/2016.
 */
public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MyViewHolder> {
    Context mContext;
    private List<MenusItem> menusList;
    OnItemClickListner menuItemClickListner;


    public MenuAdapter(Context mContext,List<MenusItem> menusList) {

        this.menusList = menusList;
        this.mContext=mContext;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView menu_item_name, menus_item_desc, menu_item_price;
        ImageView item_pic;

        public MyViewHolder(View view) {
            super(view);

            menu_item_name = (TextView) view.findViewById(R.id.item_name);
            menus_item_desc = (TextView) view.findViewById(R.id.item_description);
            menu_item_price = (TextView) view.findViewById(R.id.item_price);
            item_pic= (ImageView) view.findViewById(R.id.itemmenu_image);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(menuItemClickListner != null){

                menuItemClickListner.onItemClick(v, getPosition());
            }
        }
    }

    public interface OnItemClickListner{

        void onItemClick(View view, int position);
    }

    public void SetOnItemClickListner(final OnItemClickListner menuItemClickListner){

        this.menuItemClickListner = menuItemClickListner;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_menu_row, parent, false);
       // View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_menu_item_row, parent, false);


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        MenusItem menus = menusList.get(position);
       // Image menuImage=menusList.get(position)
       // String url=menus.getImages().get(position).getUrl();

            holder.menu_item_name.setText("" + menus.getName());

            holder.menus_item_desc.setText("" + menus.getDescription());
            holder.menu_item_price.setText("Rs" + " " + (int) menus.getPrice());

        if(menus.getImages().size() > 0) {
            Picasso.with(mContext).load(menus.getImages().get(0).getUrl()).
                    placeholder(R.drawable.progress_animation).resize(100, 100).into(holder.item_pic);
        }else{
            holder.item_pic.setBackgroundResource(R.drawable.fastfood);
        }

//        if (position % 2 == 1) {
//            holder.itemView.setBackgroundColor(Color.parseColor("#B7B7B7"));
//            //holder.itemView.setBackgroundColor(Color.parseColor("#dddddd"));
//
//        } else {
//           // holder.itemView.setBackgroundColor(R.color.lightBackground_black);
//           // holder.itemView.getResources().getColor(R.color.lightBackground_black);
//            holder.itemView.setBackgroundColor(Color.parseColor("#161616"));
//
//        }


    }

    @Override
    public int getItemCount() {
        return menusList.size();
    }


}

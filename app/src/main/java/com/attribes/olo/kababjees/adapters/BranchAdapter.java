package com.attribes.olo.kababjees.adapters;

import android.app.Service;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.attribes.olo.kababjees.R;
import com.attribes.olo.kababjees.models.Branches;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maaz on 8/25/2016.
 */
public class BranchAdapter extends BaseAdapter {

    Context mContext;
    private ArrayList<Branches> branchesList;
    int Holderid;
    String firstElement;
    boolean isFirstTime;


    public BranchAdapter(Context mContext, ArrayList<Branches> branchesList, String defaultText) {
        this.mContext = mContext;
        this.branchesList = branchesList;

        setDefaultText(defaultText);
    }

    @Override
    public int getCount() {
        return branchesList.size();
    }

    @Override
    public Object getItem(int position) {
        return branchesList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return branchesList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }



    public View getCustomView(int position, View view, ViewGroup parent) {

        ViewHolder viewHolder = new ViewHolder();
        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Service.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.branch_item, null);

            viewHolder = createViewHolder(view);
            view.setTag(viewHolder);

        } else {

            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.branchesName.setText(branchesList.get(position).getName());
        //setBranchesName(viewHolder, position);

        return view;
    }


    private static class ViewHolder {
        private TextView branchesName;
    }

    public ViewHolder createViewHolder(View row ) {
        ViewHolder viewHolder = new ViewHolder();

        viewHolder.branchesName = (TextView) row.findViewById(R.id.branchesName);
        Holderid = 1;

        return viewHolder;
    }

    private void setDefaultText(String defaultText) {

        this.firstElement = String.valueOf(branchesList.get(0));
        branchesList.get(0).setName(defaultText) ;
    }
}

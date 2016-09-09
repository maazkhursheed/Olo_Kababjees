package com.attribes.olo.kababjees.adapters;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import com.attribes.olo.kababjees.R;
import com.attribes.olo.kababjees.models.Reservation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Maaz on 9/5/2016.
 */
public class OnlineReservationLogAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Reservation> reservationsList;
    int Holderid;

    public OnlineReservationLogAdapter(Context context, ArrayList<Reservation> reservationsList) {
        this.context = context;
        this.reservationsList = reservationsList;
    }

    @Override
    public int getCount() {
        return reservationsList.size();
    }

    @Override
    public Object getItem(int position) {
        return reservationsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        ViewHolder viewHolder = new ViewHolder();

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Service.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_item_reservation, null);

            viewHolder = createViewHolder(convertView);
            convertView.setTag(viewHolder);

        } else {

            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.resPersonName.setText("Reservation by:  "+reservationsList.get(position).getName());
        viewHolder.resPersonCount.setText("Reservation for persons:  "+String.valueOf(reservationsList.get(position).getNo_of_person()));
        viewHolder.resPersonEmail.setText("Email:  "+reservationsList.get(position).getEmail());
        viewHolder.resPersonContact.setText("Contact #:  "+reservationsList.get(position).getPhone());
        viewHolder.resTime.setText("Reservation time:  "+unixToSimpleDateTime(reservationsList.get(position).getTime()));

        if(reservationsList.get(position).getBranch_id() == 13){
            viewHolder.resBranch.setText(String.valueOf("Branch name: D.H.A, Cantt, Phase I (Lahore)"));
        }
        else  if(reservationsList.get(position).getBranch_id() == 12){
            viewHolder.resBranch.setText(String.valueOf("Branch name: Do Darya"));
        }
        else  if(reservationsList.get(position).getBranch_id() == 11){
            viewHolder.resBranch.setText(String.valueOf("Branch name: North Nazimabad"));
        }
        else  if(reservationsList.get(position).getBranch_id() == 9){
            viewHolder.resBranch.setText(String.valueOf("Branch name: M.Ali Society"));
        }
        else  if(reservationsList.get(position).getBranch_id() == 8){
            viewHolder.resBranch.setText(String.valueOf("Branch name: Shaheed-e-Millat"));
        }
        else  if(reservationsList.get(position).getBranch_id() == 7){
            viewHolder.resBranch.setText(String.valueOf("Branch name: Clifton Branch"));
        }


        final ViewHolder finalViewHolder = viewHolder;

        return convertView;
    }

    private static class ViewHolder {

        private TextView resPersonName, resPersonCount, resPersonEmail, resPersonContact, resTime, resBranch;
    }

    public ViewHolder createViewHolder(View row ) {
        ViewHolder viewHolder = new ViewHolder();

        viewHolder.resPersonName = (TextView) row.findViewById(R.id.resPersonName);
        viewHolder.resPersonCount = (TextView) row.findViewById(R.id.resPersonCount);
        viewHolder.resPersonEmail = (TextView) row.findViewById(R.id.resPersonEmail);
        viewHolder.resPersonContact = (TextView) row.findViewById(R.id.resPersonContact);
        viewHolder.resTime = (TextView) row.findViewById(R.id.resTime);
        viewHolder.resBranch = (TextView) row.findViewById(R.id.resBranch);
        Holderid = 1;

        return viewHolder;
    }

    public String unixToSimpleDateTime(Long obtainedUnixDateTime){

        long unixSeconds = obtainedUnixDateTime;
        Date date = new Date(unixSeconds*1000L); // *1000 is to convert seconds to milliseconds
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm"); // the format of your date
        sdf.setTimeZone(TimeZone.getTimeZone("gmt+5")); // give a timezone reference for formating (see comment at the bottom
        String formattedTimeStamp = sdf.format(date);
        return formattedTimeStamp;
    }
}

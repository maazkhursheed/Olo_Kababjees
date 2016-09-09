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
import com.attribes.olo.kababjees.adapters.OnlineReservationLogAdapter;
import com.attribes.olo.kababjees.models.Reservation;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Maaz on 9/5/2016.
 */
public class OnlineReservationsViewFragment extends Fragment {

    private View view;
    private ListView reservationsList;
    private FrameLayout no_ReserLayout;

    ArrayList<Reservation> reservationLogList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view =inflater.inflate(R.layout.fragment_online_reservations_list, container, false);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        intViews(view);

        SharedPreferences mPrefs = getActivity().getSharedPreferences("Reservation_Pref", MODE_PRIVATE);
        reservationLogList = new ArrayList<>();
        Gson gson = new Gson();
        String jsonReservations = mPrefs.getString("ReservationLogList",null);

        if(jsonReservations != null){
            Type type = new TypeToken<ArrayList<Reservation>>(){}.getType();
            ArrayList<Reservation> reservationObtainedList = gson.fromJson(jsonReservations, type);
            reservationLogList = reservationObtainedList ;
        }


        prepareReservationsList();

        return view;

    }

    private void intViews(View view) {
        reservationsList = (ListView) view.findViewById(R.id.reservationLogList);
        no_ReserLayout = (FrameLayout) view.findViewById(R.id.noReservationLayout);
    }


    private void prepareReservationsList(){

        if(reservationLogList.isEmpty()){
            reservationsList.setVisibility(View.GONE);
            no_ReserLayout.setVisibility(View.VISIBLE);
        }
        else {

            OnlineReservationLogAdapter onlineReservationLogAdapter = new OnlineReservationLogAdapter(getActivity(),reservationLogList);
            reservationsList.setAdapter(onlineReservationLogAdapter);
        }

    }


}

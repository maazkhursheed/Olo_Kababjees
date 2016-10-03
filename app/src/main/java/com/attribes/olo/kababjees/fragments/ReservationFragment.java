package com.attribes.olo.kababjees.fragments;

import android.app.*;
import android.content.Context;
import android.content.Intent;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import android.content.SharedPreferences;
import android.os.Bundle;
//import android.app.DatePickerDialog;
//import android.app.Dialog;
//import android.app.TimePickerDialog;
import android.os.StrictMode;
import android.text.format.DateFormat;
import android.text.method.KeyListener;
import android.view.*;
import android.widget.*;
import com.attribes.olo.kababjees.R;
import com.attribes.olo.kababjees.Screens.MainActivity;
import com.attribes.olo.kababjees.Screens.ModeSelection;
import com.attribes.olo.kababjees.adapters.BranchAdapter;
import com.attribes.olo.kababjees.models.Branches;
import com.attribes.olo.kababjees.models.OrderResponse;
import com.attribes.olo.kababjees.models.Reservation;
import com.attribes.olo.kababjees.network.RestClient;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wdullaer.materialdatetimepicker.time.Timepoint;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import com.wdullaer.materialdatetimepicker.Utils;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Maaz on 8/24/2016.
 */
public class ReservationFragment extends Fragment implements TimePickerDialog.OnTimeSetListener,DatePickerDialog.OnDateSetListener{

    private View view;
    private ProgressDialog progressDialog;
    private static EditText  getDateandTime;
    private EditText getName, getEmail, getPhone, getPersonsCount;
    Spinner spinnerBranch;
    private Button submit;
    private TextView clearDatenTime;
    static String date;
    static String time;
    static String dateTime;
    String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    ArrayList<Reservation> reservationLogList;
    Gson gson;
    String jsonOnlineReservations;
    Object branch;
    String kbjBranch;
    int branchId;
    KeyListener mKeyListener ;
    SharedPreferences mPrefs ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view =inflater.inflate(R.layout.fragment_resrevation, container, false);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        initViews(view);

//        mPrefs =getActivity().getPreferences(MODE_PRIVATE);
        mPrefs = getActivity().getSharedPreferences("Reservation_Pref", MODE_PRIVATE);
        reservationLogList = new ArrayList<>();
        gson = new Gson();
        String jsonReservations = mPrefs.getString("ReservationLogList",null);

        if(jsonReservations != null){
            Type type = new TypeToken<ArrayList<Reservation>>(){}.getType();
            ArrayList<Reservation> reservationObtainedList = gson.fromJson(jsonReservations, type);
            reservationLogList = reservationObtainedList ;
        }

        setHasOptionsMenu(true);
        return view;

    }

    private void disable_enableEditTextListner() {

        if(kbjBranch !="MAS" || kbjBranch != "CF" || kbjBranch != "DD" || kbjBranch != "NN" ||
                kbjBranch != "SM" || kbjBranch != "TR PECHS" ) {
            mKeyListener = getDateandTime.getKeyListener();
            getDateandTime.setKeyListener(null);
            getDateandTime.setError("Select the branch first");
            Toast.makeText(getActivity(),"Select the branch first",Toast.LENGTH_SHORT).show();
        }
        else{
            getDateandTime.setKeyListener(mKeyListener);
        }
    }

    private void initViews(View view) {

        getActivity().setTitle("Reserve your seat");
        getDateandTime = (EditText) view.findViewById(R.id.edt_datetime);
        getName = (EditText) view.findViewById(R.id.edt_name);
        getEmail = (EditText) view.findViewById(R.id.edt_mail);
        getPhone = (EditText) view.findViewById(R.id.edt_phone);
        getPersonsCount = (EditText) view.findViewById(R.id.edt_person_count);
        clearDatenTime = (TextView) view.findViewById(R.id.clearTv);
        clearDatenTime.setOnClickListener(new ClearDateTimeListner());
        spinnerBranch = (Spinner) view.findViewById(R.id.spinnerBranch);
        getBranches();

        spinnerBranch.setOnItemSelectedListener(new BranchSelectListner());
        getDateandTime.setOnTouchListener(new DateTimePickListner());
        //disable_enableEditTextListner();

        submit = (Button) view.findViewById(R.id.btnSubmit);
        submit.setOnClickListener(new ReservationListner());
    }


    private class ReservationListner implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            Matcher matcherObj = Pattern.compile(EMAIL_PATTERN).matcher(getEmail.getText().toString());

            if(getDateandTime.getText().toString().isEmpty()||getName.getText().toString().isEmpty()||
                    getEmail.getText().toString().isEmpty()|| getPhone.getText().toString().isEmpty()||
                    getPersonsCount.getText().toString().isEmpty() || branchId == 0) {

                Toast.makeText(getActivity().getApplicationContext(),"Fields are missing or incorrect",Toast.LENGTH_SHORT).show();
            }

            else if(!matcherObj.matches()){
                getEmail.setError("Incorrect Email");
            }
            else if(getPersonsCount.getText().toString().equals("0") ){

                getPersonsCount.setError("Person count can't be zero");
            }

            else {

                long  time = getDateTimeinUnix();
                String name = getName.getText().toString();
                String email = getEmail.getText().toString();
                String personContact = getPhone.getText().toString();
                int personCount = Integer.parseInt(getPersonsCount.getText().toString());
                int branchID = branchId;

                if (personCount < 2) {
                    getPersonsCount.setError("Reservation can be done for minimum of 2 persons");
                }

                else{

                    Reservation reservation = new Reservation(time, personCount, personContact, name, branchID, email);

                    reservationLogList.add(reservation);
                    SharedPreferences.Editor prefsEditor = mPrefs.edit();
                    jsonOnlineReservations = gson.toJson(reservationLogList);
                    prefsEditor.putString("ReservationLogList", jsonOnlineReservations);
                    prefsEditor.commit();

                    showProgress(" Submitting .....");
                    RestClient.getAdapter().placeReservation(reservation, new Callback<OrderResponse>() {
                    @Override
                    public void success(OrderResponse orderResponse, Response response) {
                        hideProgress();
                        Toast.makeText(getActivity(), "Your reservation has been successfully placed ! You will soon receive a confirmation call", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getActivity(), ModeSelection.class);
                        startActivity(intent);
                    }

                    @Override
                    public void failure(RetrofitError error) {

                    }
                });

                }
            }
        }
    }

    private void getBranches() {

        showProgress("Getting Branches ...");

        RestClient.getAdapter().getBranches(new Callback<ArrayList<Branches>>() {
            @Override
            public void success(ArrayList<Branches> branches, Response response) {

                hideProgress();
                Collections.reverse(branches);
                BranchAdapter branchAdapter = new BranchAdapter(getActivity(),branches,"Select the branch :");
                spinnerBranch.setAdapter(branchAdapter);
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }


    private void showProgress(String message){
        progressDialog= ProgressDialog.show(getActivity(),"",message,false);
    }

    private void hideProgress(){
        progressDialog.dismiss();
    }

//-------------------------------------------- Static classes for Date and Time Picker ---------------------------------------


//    public static class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener{
//
//        @Override
//        public Dialog onCreateDialog(Bundle savedInstanceState) {
//            // Use the current time as the default values for the picker
//            final Calendar c = Calendar.getInstance();
//            hour = c.get(Calendar.HOUR_OF_DAY);
//            minute = c.get(Calendar.MINUTE);
//
//
//            // Create a new instance of TimePickerDialog and return it
//            TimePickerDialog timePickDialogue = new TimePickerDialog(getActivity(), this, hour, minute,
//                                                                     DateFormat.is24HourFormat(getActivity()));
//            return timePickDialogue;
//        }
//
//        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
//            // Do something with the time chosen by the user
//            if(getDateandTime.getText()!= null) {
//
//                time = hourOfDay + ":" + minute ;
//                getDateandTime.setText(date+" "+time);
//            }
//        }
//    }


//    public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
//
//        @Override
//        public Dialog onCreateDialog(Bundle savedInstanceState) {
//            // Use the current date as the default date in the picker
//            final Calendar c = Calendar.getInstance();
//            year = c.get(Calendar.YEAR);
//            month = c.get(Calendar.MONTH);
//            day = c.get(Calendar.DAY_OF_MONTH);
//
//            // Create a new instance of DatePickerDialog and return it
//            return new DatePickerDialog(getActivity(), this, year, month, day);
//        }
//
//        public void onDateSet(DatePicker view, int year, int month, int day) {
//            // Do something with the date chosen by the user
//            date = day + "/" + (month + 1) + "/" + year ;
//            getDateandTime.setText(date+" "+time);
//        }
//    }


    //----------------------------------------- End of static classes -------------------------------------------------------------

    public void TimePickerDialogue(){

        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        TimePickerDialog timePickDialogue = TimePickerDialog.newInstance(ReservationFragment.this,hour,minute,true);

//            timePickDialogue.setMinTime(24,00,0);
//            timePickDialogue.setMaxTime(12,59,0);

        Timepoint tp1 = new Timepoint(12,00,00);
        Timepoint tp1a = new Timepoint(12,15,00);
        Timepoint tp1b = new Timepoint(12,30,00);
        Timepoint tp1c = new Timepoint(12,45,00);
        Timepoint tp2 = new Timepoint(13,00,00);
        Timepoint tp2a = new Timepoint(13,15,00);
        Timepoint tp2b = new Timepoint(13,30,00);
        Timepoint tp2c = new Timepoint(13,45,00);
        Timepoint tp3 = new Timepoint(14,00,00);
        Timepoint tp3a = new Timepoint(14,15,00);
        Timepoint tp3b = new Timepoint(14,30,00);
        Timepoint tp3c = new Timepoint(14,45,00);
        Timepoint tp4 = new Timepoint(15,00,00);
        Timepoint tp4a = new Timepoint(15,15,00);
        Timepoint tp4b = new Timepoint(15,30,00);
        Timepoint tp4c = new Timepoint(15,45,00);
        Timepoint tp5 = new Timepoint(16,00,00);
        Timepoint tp5a = new Timepoint(16,15,00);
        Timepoint tp5b = new Timepoint(16,30,00);
        Timepoint tp5c = new Timepoint(16,45,00);
        Timepoint tp6 = new Timepoint(17,00,00);
        Timepoint tp6a = new Timepoint(17,15,00);
        Timepoint tp6b = new Timepoint(17,30,00);
        Timepoint tp6c = new Timepoint(17,45,00);
        Timepoint tp7 = new Timepoint(18,00,00);
        Timepoint tp7a = new Timepoint(18,15,00);
        Timepoint tp7b = new Timepoint(18,30,00);
        Timepoint tp7c = new Timepoint(18,45,00);
        Timepoint tp8 = new Timepoint(19,00,00);
        Timepoint tp8a = new Timepoint(19,15,00);
        Timepoint tp8b = new Timepoint(19,30,00);
        Timepoint tp8c = new Timepoint(19,45,00);
        Timepoint tp9 = new Timepoint(20,00,00);
        Timepoint tp9a = new Timepoint(20,15,00);
        Timepoint tp9b = new Timepoint(20,30,00);
        Timepoint tp9c = new Timepoint(20,45,00);
        Timepoint tp10 = new Timepoint(21,00,00);
        Timepoint tp10a = new Timepoint(21,15,00);
        Timepoint tp10b = new Timepoint(21,30,00);
        Timepoint tp10c = new Timepoint(21,45,00);
        Timepoint tp11 = new Timepoint(22,00,00);
        Timepoint tp11a = new Timepoint(22,15,00);
        Timepoint tp11b = new Timepoint(22,30,00);
        Timepoint tp11c = new Timepoint(22,45,00);
        Timepoint tp12 = new Timepoint(23,00,00);
        Timepoint tp12a = new Timepoint(23,15,00);
        Timepoint tp12b = new Timepoint(23,30,00);
        Timepoint tp12c = new Timepoint(23,45,00);
        Timepoint tp13 = new Timepoint(00,00,00);
        Timepoint tp13a = new Timepoint(00,15,00);
        Timepoint tp13b = new Timepoint(00,30,00);
        Timepoint tp13c = new Timepoint(00,45,00);
        //Timepoint tp13d = new Timepoint(01,00,00);

        if(kbjBranch.equals("MAS") || kbjBranch.equals("CF")) {
            Timepoint[] list = new Timepoint[]{tp1, tp1a, tp1b, tp1c, tp2, tp2a, tp2b, tp2c, tp3, tp3a, tp3b, tp3c,
                    tp4, tp4a, tp4b, tp4c, tp5, tp5a, tp5b, tp5c, tp6, tp6a, tp6b, tp6c,
                    tp7, tp7a, tp7b, tp7c, tp8, tp8a, tp8b, tp8c, tp9, tp9a, tp9b, tp9c,
                    tp10, tp10a, tp10b, tp10c, tp11, tp11a, tp11b, tp11c, tp12, tp12a,
                    tp12b, tp12c, tp13, tp13a, tp13b, tp13c};
            timePickDialogue.setSelectableTimes(list);
        }

        else if(kbjBranch.equals("DD") || kbjBranch.equals("NN") || kbjBranch.equals("SM") || kbjBranch.equals("TR PECHS")){

            Timepoint[] list = new Timepoint[]{
                    tp7, tp7a, tp7b, tp7c, tp8, tp8a, tp8b, tp8c, tp9, tp9a, tp9b, tp9c,
                    tp10, tp10a, tp10b, tp10c, tp11, tp11a, tp11b, tp11c, tp12, tp12a,
                    tp12b, tp12c, tp13, tp13a, tp13b, tp13c};
            timePickDialogue.setSelectableTimes(list);
        }

        timePickDialogue.show(getFragmentManager(),"TimePicker");

    }


    public void DatePickerDialogue(){

            Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

        c.set(year,month,day+1);
        DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(ReservationFragment.this, year, month, day);
        datePickerDialog.setMinDate(c);

        // For disabling the weekends
//        if(c.get(Calendar.DAY_OF_WEEK)== Calendar.SATURDAY || c.get(Calendar.DAY_OF_WEEK)== Calendar.SUNDAY ) {
//            Calendar[] cal = new Calendar[]{};
//            datePickerDialog.setDisabledDays(cal);
//        }
        datePickerDialog.show(getFragmentManager(),"DatePicker");

    }


    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

        final Calendar calendar = Calendar.getInstance();

        if(time.equals("0:0") || time.equals("0:15") || time.equals("0:30") || time.equals("0:45") || time.equals("1:0")){
            date = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            Date dateGotten = null;
            try {
                dateGotten = format.parse(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            //final Calendar calendar = Calendar.getInstance();
            calendar.setTime(dateGotten);
            calendar.add(Calendar.DAY_OF_YEAR, 1);

            getDateandTime.setText(format.format(calendar.getTime()) + " " + time);

        }
        else {

            date = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
            getDateandTime.setText(date + " " + time);

        }

    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {
        if(getDateandTime.getText()!= null) {
            time = hourOfDay + ":" + minute ;
            getDateandTime.setText(date+" "+time);
            DatePickerDialogue();
        }
    }



    public long getDateTimeinUnix(){

        String str_date = getDateandTime.getText().toString();
        Date localTime =null;

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");
        sdf.setTimeZone(TimeZone.getTimeZone("gmt+5"));
        try {
            localTime = sdf.parse(str_date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long unixTime = (long)localTime.getTime()/1000L;            // for int format  int unixTime = (int)localTime.getTime()/1000;
        return unixTime;
    }


    private class BranchSelectListner implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
           // On selecting a spinner item

            branch =  spinnerBranch.getSelectedItem();
            kbjBranch = ((Branches) branch).getCode();
            branchId = ((Branches) branch).getId();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

            Toast.makeText(getActivity(),"Please select the branch",Toast.LENGTH_SHORT).show();
        }
    }

    private class DateTimePickListner implements View.OnTouchListener {
        @Override
        public boolean onTouch(View v, MotionEvent event) {

            if (event.getAction() == MotionEvent.ACTION_UP) {

                if(kbjBranch.equals("MAS") || kbjBranch.equals("CF") || kbjBranch.equals("DD") ||
                        kbjBranch.equals("NN") || kbjBranch.equals("SM") || kbjBranch.equals("TR PECHS")){
                    TimePickerDialogue();
                }
                else{
                    Toast.makeText(getActivity(),"Select the branch first",Toast.LENGTH_SHORT).show();
                }
//                new TimePickerFragment().show(getFragmentManager(),"TimePicker");
//                new DatePickerFragment().show(getFragmentManager(),"DatePicker");
            }
            return true;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent parentActivityIntent = new Intent(getActivity(), ModeSelection.class);
                parentActivityIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(parentActivityIntent);
                getActivity().finish();
                break;

            // Other case statements...

            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private class ClearDateTimeListner implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            getDateandTime.setText("");
        }
    }
}

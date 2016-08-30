package com.attribes.olo.kababjees.fragments;

import android.app.*;
import android.content.Intent;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import android.os.Bundle;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.StrictMode;
import android.text.format.DateFormat;
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
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Maaz on 8/24/2016.
 */
public class ReservationFragment extends Fragment {

    private View view;
    private ProgressDialog progressDialog;
    private static EditText  getDateandTime;
    private EditText getName, getEmail, getPhone, getPersonsCount;
    Spinner spinnerBranch;
    private Button submit;
    static String date;
    static String time;
    static String dateTime;
    String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

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
        setHasOptionsMenu(true);
        return view;

    }

    private void initViews(View view) {

        getActivity().setTitle("Reserve your seat");
        getDateandTime = (EditText) view.findViewById(R.id.edt_datetime);
        getName = (EditText) view.findViewById(R.id.edt_name);
        getEmail = (EditText) view.findViewById(R.id.edt_mail);
        getPhone = (EditText) view.findViewById(R.id.edt_phone);
        getPersonsCount = (EditText) view.findViewById(R.id.edt_person_count);
        spinnerBranch = (Spinner) view.findViewById(R.id.spinnerBranch);
        getBranches();

        getDateandTime.setOnTouchListener(new DateTimePickListner());
        spinnerBranch.setOnItemSelectedListener(new BranchSelectListner());

        submit = (Button) view.findViewById(R.id.btnSubmit);
        submit.setOnClickListener(new ReservationListner());
    }


    private class ReservationListner implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            if(getDateandTime.getText().toString().isEmpty()||getName.getText().toString().isEmpty()||
                    getEmail.getText().toString().isEmpty()||getPhone.getText().toString().isEmpty()||
                    getPersonsCount.getText().toString().isEmpty()||spinnerBranch.getId()==0) {

                Toast.makeText(getActivity().getApplicationContext(),"Fields Missing",Toast.LENGTH_SHORT).show();
            }

            else {

                long  time = getDateTimeinUnix();
                String name = getName.getText().toString();
                String email = getEmail.getText().toString();
                String personContact = getPhone.getText().toString();
                int personCount = Integer.parseInt(getPersonsCount.getText().toString());
                int branchID = spinnerBranch.getId();

                Reservation reservation = new Reservation(time, personCount, personContact, name, branchID, email);

                showProgress(" Submitting .....");
                RestClient.getAdapter().placeReservation(reservation, new Callback<OrderResponse>() {
                    @Override
                    public void success(OrderResponse orderResponse, Response response) {
                        hideProgress();
                        Toast.makeText(getActivity(), "" + response.getStatus() + " Reservation has done successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void failure(RetrofitError error) {

                    }
                });

            }
        }
    }


    private void getBranches() {

        showProgress("Getting Branches ...");

        RestClient.getAdapter().getBranches(new Callback<ArrayList<Branches>>() {
            @Override
            public void success(ArrayList<Branches> branches, Response response) {

                hideProgress();
                BranchAdapter branchAdapter = new BranchAdapter(getActivity(),branches,"Select the branch :");
                spinnerBranch.setPrompt("Select the branch");
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

//-------------------------------------------- Static Classes for Date and Time Picker ---------------------------------------


    public static class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute, DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            // Do something with the time chosen by the user
            if(getDateandTime.getText()!= null) {

                time = hourOfDay + ":" + minute ;
                getDateandTime.setText(date+" "+time);
            }
        }
    }

    public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
            date = day + "/" + (month + 1) + "/" + year ;
            getDateandTime.setText(date+" "+time);
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


//----------------------------------------- End of static classes -------------------------------------------------------------

    private class BranchSelectListner implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
           // On selecting a spinner item
            String item = parent.getItemAtPosition(position).toString();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    private class DateTimePickListner implements View.OnTouchListener {
        @Override
        public boolean onTouch(View v, MotionEvent event) {

            if (event.getAction() == MotionEvent.ACTION_UP) {
                new TimePickerFragment().show(getFragmentManager(),"TimePicker");
                new DatePickerFragment().show(getFragmentManager(),"DatePicker");
            }
            return false;
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
}

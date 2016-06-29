package com.attribes.olo.kababjees.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by attribe on 6/14/16.
 */
public class DevicePreference {
    private Context mContext;
    private SharedPreferences mPrefs;
    private String KEY_AUTH_HEADER="authkey";
    private static DevicePreference mInstance;
    private static String orderHeader="d6e8a59c-9518-4998-a12a-9c97e50cebcb";

    private static String commonHeader="d6e8a59c-9518-4998-a12a-9c97e50cebcb";



    public static DevicePreference getInstance(){
        if(mInstance == null){

            mInstance =new DevicePreference();



        }

        return mInstance;

    }

    private DevicePreference() {

    }
    public void initPref(Context context){

        this.mContext = context;

        mPrefs= mContext.getSharedPreferences("clientPrefs", Context.MODE_PRIVATE);
    }


    public void setAuthHeaderFlag(Boolean flag) {


        SharedPreferences.Editor editor = mPrefs.edit();
        editor.putBoolean(KEY_AUTH_HEADER,flag);
        editor.commit();

    }

    public boolean isAuthHeaderFlag(){

        boolean headerFlag = mPrefs.getBoolean(KEY_AUTH_HEADER, false);

        return headerFlag;
    }

    public String getHeader(){

         if(isAuthHeaderFlag())
         {
             return orderHeader;

         }
        else {
             return commonHeader;

         }



    }


}

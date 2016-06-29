package com.attribes.olo.kababjees.utils;

import android.content.Context;
import android.provider.Settings;

/**
 * Created by attribe on 6/9/16.
 */
public class DeviceInfo {
    private static Context context;
    private static DeviceInfo deviceInfo;
    private static String orderHeader="7ea442c9-2fca-405f-94f6-4ebb94484f01";
    private static String commonHeader="d6e8a59c-9518-4998-a12a-9c97e50cebcb";



    public static synchronized DeviceInfo getInstance() {

        if (deviceInfo == null) {


            deviceInfo = new DeviceInfo();

        }

        return deviceInfo;

    }

    public String getDeviceID(){

        String deviceId = Settings.Secure.getString(this.context.getContentResolver(),
                Settings.Secure.ANDROID_ID);

        return deviceId;

    }


public String getHeader(){



    return commonHeader;

}


}

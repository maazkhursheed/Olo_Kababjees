package com.attribes.olo.kababjees.analytics;

import android.content.Context;
import android.os.Bundle;
import com.google.firebase.analytics.FirebaseAnalytics;

/**
 * Created by Maaz on 10/3/2016.
 */
public class AnalyticsHelper {

    private static FirebaseAnalytics mFirebaseAnalytics;

    public static void initAnalytics(Context context){

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
    }


    public static void logEvent(Bundle bundle) {

        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.BEGIN_CHECKOUT,bundle);
    }
}

package com.attribes.olo.kababjees.network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import com.attribes.olo.kababjees.Interfaces.NetworkConnectivityListener;

/**
 * Created by attribe on 6/24/16.
 */
public class NetworkChangeReceiver  extends BroadcastReceiver {

    private static NetworkChangeReceiver networkChangeReceiver;
    private static final String LOG_TAG = "CheckNetworkStatus";
    private static NetworkConnectivityListener listener;
//    private NetworkChangeReceiver() {
//    }

    public static NetworkChangeReceiver getInstance(){

        if(networkChangeReceiver == null){

            networkChangeReceiver = new NetworkChangeReceiver();
        }
        return networkChangeReceiver;
    }

    public void setConnectivityListener(NetworkConnectivityListener implementor){

        listener = implementor;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.v(LOG_TAG, "Receieved notification about com.example.maaz.olo.screens.network status");
        boolean networkAvailable = isNetworkAvailable(context);


        if(networkAvailable){
            listener.onNetworkConnetced();
        }

        if(!networkAvailable){
            listener.onNetworkDisconnected();
        }
    }

    public boolean isNetworkAvailable(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

        return isConnected ;
    }
}
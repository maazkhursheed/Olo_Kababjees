package com.attribes.olo.kababjees.network;

import com.attribes.olo.kababjees.utils.Constants;
import com.squareup.okhttp.OkHttpClient;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.client.OkClient;

import java.util.concurrent.TimeUnit;

/**
 * Created by Maaz on 5/13/2015.
 */
public class RestClient {

    private static ApiInterface mApiInterface;

    public RestClient(){

    }

    static{
        setUpRestClient();
    }

    public static void setUpRestClient() {

        RequestInterceptor interceptor = new RequestInterceptor(){

            @Override
            public void intercept(RequestFacade request) {

                request.addHeader("Content-Type","application/json");
               request.addHeader("Accept","application/json");
                request.addHeader("Authorization","d6e8a59c-9518-4998-a12a-9c97e50cebcb");
                                                  // d6e8a59c-9518-4998-a12a-9c97e50cebcb
              //  request.addHeader("Authorization", DevicePreference.getInstance().getHeader());


            }
        };

        OkHttpClient okHttpClient=new OkHttpClient();

        okHttpClient.setReadTimeout(Constants.TIMEOUT, TimeUnit.MILLISECONDS);
        okHttpClient.setConnectTimeout(Constants.TIMEOUT, TimeUnit.MILLISECONDS);



        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(Constants.BASE_URL_PROD)
                .setRequestInterceptor(interceptor)
                .setLogLevel(RestAdapter.LogLevel.FULL)

                .setClient(new OkClient(okHttpClient))
                .build();

        mApiInterface = restAdapter.create(ApiInterface.class);

    }

    public static ApiInterface getAdapter(){

        return mApiInterface;
    }

}

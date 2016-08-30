package com.attribes.olo.kababjees.network;

import com.attribes.olo.kababjees.models.*;
import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;

import java.util.ArrayList;

/**
 * Created by Maaz on 5/13/2015.
 */
public interface ApiInterface {

    public static String URL_GET_CATEGORIES ="/categories";
    public static String URL_GET_ITEMS = "/menus";
    public static String URL_PLACE_ORDER = "/orders";
    public static String URL_PLACE_RESERVATION = "/reservations";
    public static String URL_BRANCHES = "/branches";
    public static String URL_SYNC ="/categories/sync";
    public static String URL_REGISTER_DEVICE= "/devices";
    public static String URL_GET_KEY="/user/get_api_key";
    public static String PARAM_PASSCODE = "passcode";

   // @Headers("Accept:application/json")
    @GET(URL_GET_CATEGORIES)
    void getCategories(Callback<ArrayList<Category>> callback);
   // @Headers("Accept:application/json")
    @GET(URL_GET_ITEMS)
    void getMenuItems(@Query("category_id") int categoryId,
                      Callback<ArrayList<MenusItem>> callback);

    @POST(URL_PLACE_ORDER)
    void placeOrder(@Body Orders orders, Callback<OrderResponse> responseCallback);

    @POST(URL_PLACE_RESERVATION)
    void placeReservation(@Body Reservation reservation, Callback<OrderResponse> responseCallback);

    @GET(URL_BRANCHES)
    void getBranches(Callback<ArrayList<Branches>> callback);

//    @GET(URL_GET_CATEGORIES)
//    ArrayList<Category> getCategoriesSync();


}

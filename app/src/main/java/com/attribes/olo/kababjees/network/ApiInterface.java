package com.attribes.olo.kababjees.network;

import com.attribes.olo.kababjees.models.Category;
import com.attribes.olo.kababjees.models.MenusItem;
import com.attribes.olo.kababjees.models.OrderResponse;
import com.attribes.olo.kababjees.models.Orders;
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
//
//    @GET(URL_GET_CATEGORIES)
//    ArrayList<Category> getCategoriesSync();


}

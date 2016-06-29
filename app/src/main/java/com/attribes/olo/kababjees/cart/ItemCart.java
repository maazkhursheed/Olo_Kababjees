package com.attribes.olo.kababjees.cart;

import com.attribes.olo.kababjees.models.MenusItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by attribe on 6/6/16.
 */

public class ItemCart {



    public static List<MenusItem> orderableItems;
    private static ItemCart itemCart ;

    private ItemCart(){

        //empty private constructor
    }

    public static synchronized ItemCart getInstance(){

        if(itemCart == null){


            itemCart = new ItemCart();
            orderableItems=new ArrayList<MenusItem>();

        }

        return itemCart;

    }

    /**This method adds item in com.example.maaz.olo.screens.cart,
     * in case item already exists in the com.example.maaz.olo.screens.cart, it simply
     * updates its quantity

     *
     * @param item
     */
    public void addOrUpdateItem(MenusItem item){


       boolean isItemfound = false;

        if(!orderableItems.isEmpty()) {
            for (MenusItem iterator : orderableItems) {

                //Item Update Block
                if (iterator.getId() == item.getId()) {    //Check if item exixts in com.example.maaz.olo.screens.cart
                    isItemfound = true;
                    //int tempItemQuantity =+ item.getDesiredQuantity();


                    iterator.setDesiredQuantity(iterator.getDesiredQuantity()+item.getDesiredQuantity());

                   /// iterator.setDesiredQuantity(item.getDesiredQuantity());

                    break;
                }



            }

            if(!isItemfound){
                orderableItems.add(item);

            }

        }

        else{

            orderableItems.add(item);

    }

    }



    /**
     * This method removes an item in com.example.maaz.olo.screens.cart if exist
     * @param item
     */
    public void removeItem(MenusItem item)
    {
        for(MenusItem iterator : orderableItems) {

            //Item remove Block
            if (iterator.getId() == item.getId()) {    //Check if item exixts in com.example.maaz.olo.screens.cart
                orderableItems.remove(item);

                 break;

            }
        }
    }

    /**
     * This method simply returns  Cart total price
     *
     * @return
     */


    public double getTotal(){
        double totalItemPrice = 0;
        double cartTotal=0;

        for(MenusItem item : orderableItems){

            totalItemPrice =item.getDesiredQuantity() * item.getPrice();
            cartTotal += totalItemPrice;
        }

        return cartTotal;
    }
    /**
     * This method simply returns  Cart total price plus some other charges
     *
     * @return
     */

    public double getAllTotal(){

        double allTotalPrice = 0;
        int deliveryFee = 0;
        double serviceFee = 0;

        allTotalPrice = getTotal()+ deliveryFee + serviceFee;
        return allTotalPrice;
    }

    public static List<MenusItem> getOrderableItems() {
        return orderableItems;
    }

    public MenusItem checkItem(MenusItem menusItem) {

        MenusItem menuItem = null;
        for(MenusItem item: orderableItems){

            if(menusItem.getId() == item.getId()){

                menuItem = item;
            }




        }

        return menuItem;
    }
}

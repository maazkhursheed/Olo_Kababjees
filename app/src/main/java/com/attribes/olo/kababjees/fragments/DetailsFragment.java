package com.attribes.olo.kababjees.fragments;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.attribes.olo.kababjees.Interfaces.OnDrawerEnableDisable;
import com.attribes.olo.kababjees.Interfaces.OnDrawerToggleListner;
import com.attribes.olo.kababjees.cart.ItemCart;
import com.attribes.olo.kababjees.models.MenusItem;

import com.attribes.olo.kababjees.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailsFragment extends Fragment {
   // private View view;
    private TextView label_itemName,
            label_itemPrice,
            label_incrementQuantity,
            label_decrementQuantity,
            label_totalPrice_according_quantity,
            label_quantity,
            Label_selected_quantity_txtview,
            label_selected_quantity;

    private ProgressBar progressBar;
    private ProgressDialog progressDialog;
    private Button button_addCart;
    private double itemPrice;
    static double totalprice;
    static double total_cart_bill;
    private boolean valuechanged = false;
    private int initialQuantity = 1, maxQuantity =999 ;
    private int selectedItemQuantity = 1;
    private int alreadyIteamQuantity=0;

    //private int initialItemQuantity;
    private String itemname;
    private OnDetailFragmentInteraction listner;
    private OnDrawerEnableDisable enableDisableDrawer;
    private MenusItem menusItem;
    private OnDrawerToggleListner mListner;
   // private ProgressDialog progressDialog;
    private ImageView itemView;
    private DrawerLayout mDrawerLayout;


    public DetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View rootview  = inflater.inflate(R.layout.fragment_details, container, false);


        //showProgress("Loading...");
        getIntentValues();
        initViews(rootview);


        setHasOptionsMenu(true);
        //backToMenus(menusItem.getId());
        return rootview;
    }


    private void initViews(View view) {
        //toolbar = (Toolbar) findViewById(R.id.toolbar_details_item_screen);

        // ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Details Screen");
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        itemView = (ImageView) view.findViewById(R.id.itemFullView);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar1);
        button_addCart = (Button) view.findViewById(R.id.addcart_btn);
        label_itemName = (TextView) view.findViewById(R.id.textView_itemname);
        label_itemPrice = (TextView) view.findViewById(R.id.textView_itemPrice);
        label_incrementQuantity = (TextView) view.findViewById(R.id.add_txtview);
        label_decrementQuantity = (TextView) view.findViewById(R.id.subtract_txtview);
        label_totalPrice_according_quantity = (TextView) view.findViewById(R.id.total_price);
        label_quantity = (TextView) view.findViewById(R.id.itemquantity_txtview);
        label_selected_quantity= (TextView) view.findViewById(R.id.item_quantity_already);
        Label_selected_quantity_txtview= (TextView) view.findViewById(R.id.alreaty_itemQuantity_txtview);

        //selectedItemQuantity = checkItemQuantity();
        alreadyIteamQuantity=checkItemQuantity();

        //label_quantity.setText("Quantity " + selectedItemQuantit);
        label_quantity.setText("Quantity " + initialQuantity);

        if(alreadyIteamQuantity < 1){

            label_selected_quantity.setVisibility(View.INVISIBLE);
            Label_selected_quantity_txtview.setVisibility(View.INVISIBLE);

        }
        else {

            label_selected_quantity.setVisibility(View.VISIBLE);
            Label_selected_quantity_txtview.setVisibility(View.VISIBLE);

            label_selected_quantity.setText("" + alreadyIteamQuantity);
        }


        label_itemName.setText(itemname);
        label_itemPrice.setText("Rs " + String.valueOf(itemPrice));


//        set total price with quantity

        //totalprice = (calcTotalPrice(checkItemQuantity(), itemPrice));

        menusItem.setDesiredQuantity(selectedItemQuantity);
        showImage();


        //setHomeButton();

        setInitial_itemPrice();

        setListeners();


    }

    /**
     * This method determines the pre selected quantity of item, if it
     * already exists in  com.example.maaz.olo.screens.cart
     */
    private int checkItemQuantity() {

        //int desiredQuantity = initialQuantity;
        int desiredQuantity = alreadyIteamQuantity;

        MenusItem tempItem = null;
        tempItem = ItemCart.getInstance().checkItem(menusItem);

        if (tempItem != null) {
            desiredQuantity = tempItem.getDesiredQuantity();
        }


        return desiredQuantity;
    }

    private void setListeners() {
        button_addCart.setOnClickListener(new AddCartListener());
        label_incrementQuantity.setOnClickListener(new IncrementListener());
        label_decrementQuantity.setOnClickListener(new DecrementListener());
    }
    private void showImage(){


        if (menusItem.getImages().size() > 0) {
            // showProgress("Loading.....");

//            Picasso.with(getActivity()).load(menusItem.getImages().get(0).getUrl()).
//                    placeholder(R.drawable.progress_animation).into(itemView);


            Picasso.with(getActivity().getApplicationContext()).load(menusItem.getImages().get(0).getUrl()).
                  into(itemView, new Callback() {
                      @Override
                      public void onSuccess() {
                          progressBar.setVisibility(View.GONE);



                      }

                      @Override
                      public void onError() {

                      }
                  });

            // hideProgress();
            //.resize(640,344)
        } else {
            itemView.setBackgroundResource(R.drawable.fastfood);
        }

        }


    /**
     * This method receives intent values from previous screen and set them in
     * appropriate instance variables
     */
    private void getIntentValues() {

        Bundle bundle = getArguments();
        menusItem = (MenusItem) bundle.getSerializable("Item");
        itemname = menusItem.getName();
        itemPrice = menusItem.getPrice();

//        if (menusItem.getImages().size() > 0) {
//            // showProgress("Loading.....");
//
////            Picasso.with(getActivity()).load(menusItem.getImages().get(0).getUrl()).
////                    placeholder(R.drawable.progress_animation).into(itemView);
//
//
//            Picasso.with(getActivity()).load(menusItem.getImages().get(0).getUrl()).
//                  into(itemView, new Callback() {
//                      @Override
//                      public void onSuccess() {
//                          progressBar.setVisibility(View.GONE);
//
//                      }
//
//                      @Override
//                      public void onError() {
//
//                      }
//                  });
//
//            // hideProgress();
//            //.resize(640,344)
//        } else {
//            itemView.setBackgroundResource(R.drawable.fastfood);
//        }


    }

    private void hideProgress() {
        progressDialog.dismiss();
    }

    private void showProgress(String message) {
        progressDialog = ProgressDialog.show(getActivity(), "", message, false);
    }

    //set initial total price total price in txtview when activity created
    private void setInitial_itemPrice() {

        calcTotalPrice(selectedItemQuantity, itemPrice);
        if (label_totalPrice_according_quantity.getText().equals("")) {
            // label_totalPrice_according_quantity.setText("" + itemPrice);
           // label_totalPrice_according_quantity.setText("Rs" + " " + totalprice);
            label_totalPrice_according_quantity.setText("Rs" + " " + itemPrice);

        }

    }


    private double calcTotalPrice(int quantity, double itemprice) {

        return quantity * itemprice;
    }

    private void backToMenus(int cat_id) {
        Bundle bundle = new Bundle();
        bundle.putInt("Category_id", cat_id);
        MenusFragment menufragment = new MenusFragment();
        menufragment.setArguments(bundle);
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frame_container, menufragment).commit();


    }


//========================================Overridden methods=======================================================

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //return super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case android.R.id.home:

                MenusFragment menusFragment = new MenusFragment();
                FragmentManager frgmentManager = getFragmentManager();
                frgmentManager.beginTransaction().replace(R.id.frame_container, menusFragment).commit();

               // break;

        return true;

        // Other case statements...

        default:
        return super.onOptionsItemSelected(item);
    }

    }

    @Override
   public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    //((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Details Screen");



}

    @Override
    public void onResume() {
        super.onResume();
       mListner.showDrawerToggle(false);
        enableDisableDrawer.lockDrawer();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {

            listner = (OnDetailFragmentInteraction) context;
            mListner= (OnDrawerToggleListner) context;
            enableDisableDrawer =(OnDrawerEnableDisable) context;
        }
        catch (ClassCastException ex) {

            throw new ClassCastException(ex.toString() +
                    "must implement OnDetailFragmentInteractionListener");
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        try {
            listner = (OnDetailFragmentInteraction) getActivity();
            mListner= (OnDrawerToggleListner) getActivity();
            this.enableDisableDrawer= (OnDrawerEnableDisable) getActivity();

        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString()
                    + " must implement OnDetailFragmentInteractionListener");
        }
    }

    //==========================================Inner Classes / Listeners=========================================
    private class AddCartListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {


           ItemCart.getInstance().addOrUpdateItem(menusItem);
           // total_cart_bill +=totalprice;
            listner.OnItemAddedInCart();
           // getActivity().getFragmentManager().popBackStack();
           // getActivity().getFragmentManager().beginTransaction().hide(DetailsFragment.this).commit();
            backToMenus(menusItem.getCategory_id());
           // getActivity().getFragmentManager().beginTransaction().replace();











            //finish();
        }
    }

    private class IncrementListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if(selectedItemQuantity== initialQuantity ||selectedItemQuantity <maxQuantity)
            {
                selectedItemQuantity++;
                label_quantity.setText("Quantity "+selectedItemQuantity);
                totalprice= calcTotalPrice(selectedItemQuantity, itemPrice);
                label_totalPrice_according_quantity.setText("Rs" + " "+totalprice);
                menusItem.setDesiredQuantity(selectedItemQuantity);

            }
        }
    }

    private class DecrementListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if(selectedItemQuantity> initialQuantity)
            {

                selectedItemQuantity--;
                //selectedItemQuantity=initialQuantity;
                label_quantity.setText("Quantity "+selectedItemQuantity);
                totalprice= calcTotalPrice(selectedItemQuantity, itemPrice);
                label_totalPrice_according_quantity.setText("Rs" + " "+totalprice);
                menusItem.setDesiredQuantity(selectedItemQuantity);

            }
        }
    }


    public interface OnDetailFragmentInteraction{

        void OnItemAddedInCart();
    }




}


package com.attribes.olo.kababjees.Screens;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import com.attribes.olo.kababjees.Interfaces.*;
import com.attribes.olo.kababjees.adapters.CategoryAdapter;
import com.attribes.olo.kababjees.adapters.DrawerExpandableListAdapter;
import com.attribes.olo.kababjees.cart.ItemCart;
import com.attribes.olo.kababjees.fragments.*;
import com.attribes.olo.kababjees.models.Category;
import com.attribes.olo.kababjees.models.MenusItem;
import com.attribes.olo.kababjees.network.NetworkChangeReceiver;
import com.attribes.olo.kababjees.network.RestClient;
import com.attribes.olo.kababjees.utils.Constants;
import com.attribes.olo.kababjees.R;
import com.attribes.olo.kababjees.utils.DrawerGroupItems;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements DetailsFragment.OnDetailFragmentInteraction,
        OnItemRemoveListener,
        OnQuantityChangeListener,
        OnDrawerToggleListner,
        OnDrawerEnableDisable,
        NetworkConnectivityListener {

    private DrawerLayout mDrawerLayout;
    public static OnItemRemoveListener onItemRemoveListener = null;
    public static OnQuantityChangeListener onQuantityChangeListener = null;
    private ExpandableListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;        // used to store app title
    private String[] navMenuTitles;    // slide menu items
    private TypedArray navMenuIcons;
    private Toolbar toolbar;
    private ArrayList<Category> navCategoryItems;
//    private CategoryAdapter categoryAdapter;
    private DrawerExpandableListAdapter expandableListAdapter;
    private ProgressDialog progressDialog;
    Category category;
    ImageView internetImage;
    ImageView wrongImage;
    Button reserve ;
    ArrayList<DrawerGroupItems> drawerGroupItems;
    ArrayList<String> categoryList;
    ArrayList<Category> actualCategories;

    private static final String LOG_TAG = "CheckNetworkStatus";
   // private NetworkChangeReceiver receiver;
    private boolean isConnected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NetworkChangeReceiver.getInstance().setConnectivityListener(this);
        init_views();
       // checkInternetConectivity();

        getCategory();     //show category on Drawer
        setDraweropened();    //always open a drawer when activity is opened
        drawer_Toggle_Handling(savedInstanceState);    //  enabling action bar app icon and behaving it as toggle button
       // mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

    }

    @Override
    protected void onStart() {
        super.onStart();
        NetworkChangeReceiver.getInstance().setConnectivityListener(this);
//        getCategory();
    }


    @Override
    protected void onResume() {
        super.onResume();

        ComponentName component=new ComponentName(this, NetworkChangeReceiver.class);
        getPackageManager().setComponentEnabledSetting(component,
                            PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                            PackageManager.DONT_KILL_APP);
        NetworkChangeReceiver.getInstance().setConnectivityListener(this);
        getCategory();
    }

    public void setDraweropened() { mDrawerLayout.openDrawer(mDrawerList); }

    public void setDrawerclosed(){
        mDrawerLayout.closeDrawer(mDrawerList);
    }


    private void drawer_Toggle_Handling(Bundle savedInstanceState) {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, null, R.string.app_name, R.string.app_name) {

            public void onDrawerClosed(View view) {
                // getSupportActionBar().setTitle(mTitle);
                ////set actionbar tittle when closed
                // getSupportActionBar().setTitle("Kababjees Menu");
                invalidateOptionsMenu();  // calling onPrepareOptionsMenu() to show action bar icons
            }

            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu();  // calling onPrepareOptionsMenu() to hide action bar icons
                mDrawerList.setAdapter(expandableListAdapter);

            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        if (savedInstanceState == null) {
            // on first time display view for first nav item
            //displayView(0);
        }
    }


    private void init_views()

    {
        if (Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        internetImage = (ImageView)findViewById(R.id.internetImage);
        wrongImage = (ImageView) findViewById(R.id.wrongImage);

        toolbar= (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        mTitle = mDrawerTitle = getSupportActionBar().getTitle();
        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);      // load slide menu items
        navMenuIcons = getResources().obtainTypedArray(R.array.nav_drawer_icons);    // nav drawer icons from resources

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ExpandableListView) findViewById(R.id.list_slidermenu);
        mDrawerList.setOnChildClickListener(new ChildItemListner());

        LayoutInflater inflater = getLayoutInflater();
        View listHeaderView = inflater.inflate(R.layout.drawer_header,null, false);

       // mDrawerList.addHeaderView(listHeaderView);
        mDrawerList.addHeaderView(listHeaderView,null,false);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        onItemRemoveListener = this;
        onQuantityChangeListener = this;

    }

    private void getCategory() {

        // showProgress("Loading......!");

       // showProgress("Loading.....");
        RestClient.getAdapter().getCategories(new Callback<ArrayList<Category>>() {

            @Override
            public void success(ArrayList<Category> categories, Response response) {
                //hideProgress();
                categories.size();

                actualCategories = categories;

                 categoryList = new ArrayList<String>();

                for( Category groupCategoryItems : categories){

                    categoryList.add(groupCategoryItems.getName());
                }

                makeDrawerList();
               // categoryAdapter = new CategoryAdapter(getApplicationContext(), categories);
               // mDrawerList.setAdapter(categoryAdapter);
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                Toast.makeText(getApplicationContext(), Constants.Server_Error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void makeDrawerList(){

        drawerGroupItems= new ArrayList<DrawerGroupItems>();

        ArrayList<String> childListOrder= new ArrayList<String>();
        childListOrder.add("Online Order");
        childListOrder.add("My reservations");
        DrawerGroupItems drawerGroupOrder = new DrawerGroupItems("My Order",childListOrder,null);
        drawerGroupItems.add(drawerGroupOrder);

        ArrayList<String> childListReservation= new ArrayList<String>();
        childListReservation.add("Make Reservation");
        DrawerGroupItems drawerGroupReservation = new DrawerGroupItems("Reservation",childListReservation,null);
        drawerGroupItems.add(drawerGroupReservation);

        DrawerGroupItems drawerGroupMenu = new DrawerGroupItems("Menu",categoryList,null);
        drawerGroupItems.add(drawerGroupMenu);

        expandableListAdapter = new DrawerExpandableListAdapter(MainActivity.this,drawerGroupItems);
        mDrawerList.setAdapter(expandableListAdapter);
    }


    private void showProgress(String message) {
        progressDialog=ProgressDialog.show(this,"",message,false);
    }

    private void hideProgress() { progressDialog.dismiss();}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
       // menu.findItem(R.id.cart_text).setVisible(false);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // toggle nav drawer on selecting action bar app icon/title
        if (mDrawerToggle.isDrawerIndicatorEnabled() && mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle action bar actions click
        switch (item.getItemId()) {

            case R.id.cart_text:
            case R.id.cart:

                OrderCheckoutFragment orderCheckoutFragment = new OrderCheckoutFragment();
                FragmentManager ofragmentManager = getFragmentManager();
                ofragmentManager.beginTransaction().replace(R.id.frame_container, orderCheckoutFragment).commit();

                break;

            case android.R.id.home:
                Fragment currentFragment = getFragmentManager().findFragmentById(R.id.frame_container);

                if (currentFragment instanceof DetailsFragment) {
                    //show list of category

                    MenusItem menuItem = (MenusItem) currentFragment.getArguments().getSerializable("Item");

                    int category_id = menuItem.getCategory_id();
                    MenusFragment menufragment = new MenusFragment();
                    Bundle bundle = new Bundle();
                    bundle.putInt("Category_id", category_id);

                    menufragment.setArguments(bundle);
                    FragmentManager mfragmentManager = getFragmentManager();
                    mfragmentManager.beginTransaction().replace(R.id.frame_container, menufragment).commit();

                }

                if (currentFragment instanceof UserInfo) {

                    //show OrderCheckoutFragment

                    OrderCheckoutFragment ordeCheckoutFragment = new OrderCheckoutFragment();
                    FragmentManager frgmentManager = getFragmentManager();
                    frgmentManager.beginTransaction().replace(R.id.frame_container, ordeCheckoutFragment).commit();
                }

                break;
        }
                return true;

    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // if nav drawer is opened, hide the action items
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);

       menu.findItem(R.id.cart_text).setTitle("Rs:"+ ItemCart.getInstance().getTotal());

        if(ItemCart.getOrderableItems().isEmpty()){
//            hide order checkout button
            menu.findItem(R.id.cart_text).setVisible(false);
            menu.findItem(R.id.cart).setVisible(false);
        }
        else {

            menu.findItem(R.id.cart_text).setVisible(true);
            menu.findItem(R.id.cart).setVisible(true);
            menu.findItem(R.id.cart_text).setTitle("Rs "+ ItemCart.getInstance().getTotal());
        }


        return super.onPrepareOptionsMenu(menu);
    }

//====================================Local Interface overriden methods ====================================//
    @Override
    public void OnItemAddedInCart() {
        invalidateOptionsMenu();
//        Toast.makeText(getApplicationContext(),"Total "+ItemCart.getInstance().getTotal(),Toast.LENGTH_LONG).show();
    }


    @Override
    public void onItemRemoved(int price) { invalidateOptionsMenu();}

    @Override
    public void onQuantityChanged(int price) {invalidateOptionsMenu();}

    @Override
    public void showDrawerToggle(boolean showToggle) { mDrawerToggle.setDrawerIndicatorEnabled(showToggle);}

    @Override
    public void lockDrawer() {
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

    }

    @Override
    public void unlockDrawer() {
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);


    }

    @Override
    public void onNetworkConnetced() {
        Toast.makeText(getApplicationContext(), Constants.Internet_Connected, Toast.LENGTH_SHORT).show();
        getCategory();
        Fragment currentFragment = getFragmentManager().findFragmentById(R.id.frame_container);

        if (currentFragment instanceof DetailsFragment || currentFragment instanceof UserInfo ||currentFragment instanceof OrderCheckoutFragment) {
            setDrawerclosed();
            //  setDraweropened();
        }

        else {
           setDraweropened();
        }
    }

    @Override
    public void onNetworkDisconnected() {
        //mDrawerLayout.closeDrawers();
        setDrawerclosed();

        Toast.makeText(getApplicationContext(), Constants.No_Internet_Connection,Toast.LENGTH_SHORT).show();
    }


    //====================================End of Local Interface overriden methods ====================================//

//    private class SlideMenuClickListener implements AdapterView.OnItemClickListener {
//        @Override
//        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//            category= (Category) parent.getItemAtPosition(position);
//            send_CategoryId(category.getId());
//            mDrawerLayout.closeDrawer(mDrawerList);
//            getSupportActionBar().setTitle(category.getName());
//
//        }
//    }
    private void  send_CategoryId(int cat_id)
    {
        Bundle bundle=new Bundle();
        bundle.putInt("Category_id",cat_id);
       // bundle.putSerializable("Category",);
        MenusFragment menufragment = new MenusFragment();
        menufragment.setArguments(bundle);
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frame_container, menufragment).commit();
        mDrawerLayout.closeDrawer(mDrawerList);

    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        mDrawerToggle.syncState();          // Sync the toggle state after onRestoreInstanceState has occurred.
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        mDrawerToggle.onConfigurationChanged(newConfig);      // Pass any configuration change to the drawer toggls
    }

    @Override
    public void onBackPressed() {
      // super.onBackPressed();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage("Do you want to Exit?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //MainActivity.this.finish();
                finishAffinity();
                //finish();
                ItemCart.getOrderableItems().clear();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();

    }

    @Override
    protected void onPause() {
        super.onPause();

        ComponentName component=new ComponentName(this, NetworkChangeReceiver.class);
        getPackageManager().setComponentEnabledSetting(component,
                            PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                            PackageManager.DONT_KILL_APP);
        NetworkChangeReceiver.getInstance().setConnectivityListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        NetworkChangeReceiver.getInstance().setConnectivityListener(this);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
       NetworkChangeReceiver.getInstance().setConnectivityListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        NetworkChangeReceiver.getInstance().setConnectivityListener(this);
    }


    private class ChildItemListner implements ExpandableListView.OnChildClickListener {
        @Override
        public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

          //  final String selected = (String) expandableListAdapter.getChild(groupPosition, childPosition);

            if(groupPosition == 1){
                if(childPosition == 0){
                    FrameLayout frameLayout = (FrameLayout)findViewById(R.id.frame_container) ;
                    frameLayout.setBackgroundResource(0);

                    ReservationFragment reservefragment = new  ReservationFragment();
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.frame_container, reservefragment).commit();
                }
            }

//            category= (Category) parent.getItemAtPosition(childPosition);
            if(groupPosition == 2) {
                category = actualCategories.get(childPosition);
                send_CategoryId(category.getId());
                mDrawerLayout.closeDrawer(mDrawerList);
                getSupportActionBar().setTitle(category.getName());
            }

                return true;

        }
    }
}
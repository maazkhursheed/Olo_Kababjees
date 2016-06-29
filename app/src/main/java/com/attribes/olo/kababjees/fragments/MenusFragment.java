package com.attribes.olo.kababjees.fragments;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import com.attribes.olo.kababjees.Interfaces.OnDrawerEnableDisable;
import com.attribes.olo.kababjees.Interfaces.OnDrawerToggleListner;
import com.attribes.olo.kababjees.adapters.MenuAdapter;
import com.attribes.olo.kababjees.models.MenusItem;
import com.attribes.olo.kababjees.network.RestClient;
import com.attribes.olo.kababjees.utils.Constants;
import com.attribes.olo.kababjees.R;
import com.google.gson.Gson;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

//import com.example.maaz.olo.screens.DetailScreen;

/**
 * A simple {@link Fragment} subclass.
 */
public class MenusFragment extends Fragment {

    private List<MenusItem> menuList = new ArrayList<>();
    private RecyclerView recyclerView;
    private MenuAdapter menuAdapter;
    private ProgressDialog progressDialog;
    private Gson gson;
    static String totalprice=null;
    private OnDrawerToggleListner mListner;
    private OnDrawerEnableDisable enableDisableDrawer;

    ImageView wrongImage;
   // int cat_id;

    public MenusFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_menus, container, false);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        recyclerView = (RecyclerView)rootView.findViewById(R.id.listMenu);
        wrongImage = (ImageView)rootView.findViewById(R.id.wrongImage);
        prepareMenusBy_Id(get_CatId());
       // prepareMenusBy_Id();

        return rootView;
    }
    private int get_CatId()
    {
        int cat_id=getArguments().getInt("Category_id",1);
        return cat_id;
    }

    private void prepareMenusBy_Id(int cat_id)
    {
       // if(NetworkChangeReceiver.getInstance().isNetworkAvailable(getActivity().getApplicationContext())) {

            showProgress("Loading.....");
            RestClient.getAdapter().getMenuItems(cat_id, new Callback<ArrayList<MenusItem>>() {
                @Override
                public void success(final ArrayList<MenusItem> menusItems, Response response) {
                    hideProgress();

                    if (!menusItems.isEmpty()) {
                        menuAdapter = new MenuAdapter(getActivity().getApplicationContext(), menusItems);
                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                        recyclerView.setLayoutManager(mLayoutManager);
                        recyclerView.setItemAnimator(new DefaultItemAnimator());
                        recyclerView.setAdapter(menuAdapter);

                        menuAdapter.SetOnItemClickListner(new MenuAdapter.OnItemClickListner() {
                            @Override
                            public void onItemClick(View view, int position) {

                                Bundle data = new Bundle();
                                MenusItem item = menusItems.get(position);
                                data.putSerializable("Item", (Serializable) item);
                                DetailsFragment detailsFragment = new DetailsFragment();
                                detailsFragment.setArguments(data);
                                FragmentManager fragmentManager = getFragmentManager();
                                fragmentManager.beginTransaction().replace(R.id.frame_container, detailsFragment).commit();

                            }
                        });
                    } else {
                        Toast.makeText(getActivity(), "No Menu Found of this category", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void failure(RetrofitError retrofitError) {
                    hideProgress();
                    Toast.makeText(getActivity().getApplicationContext(), Constants.Server_Error, Toast.LENGTH_LONG).show();

                    recyclerView.setVisibility(View.GONE);
//                    wrongImage.setVisibility(View.VISIBLE);
//                    Toast.makeText(getActivity(), "Something went wrong!", Toast.LENGTH_LONG).show();
                }
            });
        //}
//        else {
//            enableDisableDrawer.lockDrawer();
//            Toast.makeText(getActivity().getApplicationContext(), Constants.No_Internet_Connection,Toast.LENGTH_LONG).show();
//        }
    }


//    ==============================overriden methods ===============================//


    @Override
    public void onResume() {
        super.onResume();
        mListner.showDrawerToggle(true);
            enableDisableDrawer.unlockDrawer();

       // ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Menu Screen");

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            this.mListner = (OnDrawerToggleListner) context;
            this.enableDisableDrawer= (OnDrawerEnableDisable) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(e.toString() + " must implement OnDrawerToggleListner");
        }
    }
    @Override
    public void onStart() {
        super.onStart();
        try {

            mListner= (OnDrawerToggleListner) getActivity();
            this.enableDisableDrawer= (OnDrawerEnableDisable) getActivity();

        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString()
                    + " must implement OnDrawerToggleListner");
        }
    }


    private void showProgress(String message){
        progressDialog=ProgressDialog.show(getActivity(),"",message,false);
    }

    private void hideProgress(){progressDialog.dismiss();}

}

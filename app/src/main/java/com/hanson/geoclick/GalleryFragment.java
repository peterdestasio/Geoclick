package com.hanson.geoclick;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hanson.geoclick.Adapters.GalleryAdapter;
import com.hanson.geoclick.Helper.DBHelper;
import com.hanson.geoclick.Helper.ImageHelper;
import com.hanson.geoclick.Model.CityItem;
import com.hanson.geoclick.Model.PictureItem;

import java.util.ArrayList;

/*
This Fragment use a RecycleView to create our Android gallery.

RecyclerView is a modern, properly planned and more efficient improvement on the ListView.

This is a handy view that acts very much like a ListView but with the advantage of allowing us to scroll quickly through large data sets. It does this by only loading the images that are currently in view at any given time. This means we can load more images without the app becoming very slow. There’s a lot more that you can do with this view and it’s used all over Google’s own apps, so check out the full explanation to using RecyclerView to find out more.

 */


public class GalleryFragment extends Fragment {

    ArrayList<CityItem> picList;



    public GalleryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_gallery, container, false);

        DBHelper dbHelper = new DBHelper(this.getContext(), "Picture.db", null, 1);

        picList = dbHelper.selectPicGroupByCity();

        ArrayList<CityItem> cities =  dbHelper.selectPicGroupByCity();

        for(int i=0; i<cities.size(); i++){
            cities.get(i).setImage_id(i);
        }

        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.imagegallery);
        recyclerView.setHasFixedSize(true);
        // use of a layout manager and the adapter - we used a grid Layout manager for pictures
        // 2 is the number of the columns
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity().getApplicationContext(),2);
        recyclerView.setLayoutManager(layoutManager);

        //set the customized adapter
        GalleryAdapter adapter = new GalleryAdapter(getActivity().getApplicationContext(), cities);
        recyclerView.setAdapter(adapter);

        dbHelper.close();



        return view;
    }




}




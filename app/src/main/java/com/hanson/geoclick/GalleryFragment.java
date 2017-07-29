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

        ArrayList<CityItem> mockcities =  dbHelper.selectPicGroupByCity();

        for(int i=0; i<mockcities.size(); i++){
            mockcities.get(i).setImage_id(i);
        }

        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.imagegallery);
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity().getApplicationContext(),2);
        recyclerView.setLayoutManager(layoutManager);

        GalleryAdapter adapter = new GalleryAdapter(getActivity().getApplicationContext(), mockcities);
        recyclerView.setAdapter(adapter);



        return view;
    }




}




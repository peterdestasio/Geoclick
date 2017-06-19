package com.hanson.geoclick;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hanson.geoclick.Helper.DBHelper;
import com.hanson.geoclick.Model.PictureItem;

import java.util.ArrayList;


public class MapViewFragment extends Fragment {

    public MapViewFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        final View view = inflater.inflate(R.layout.fragment_map_view, container, false);


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        //Connect with databases
        DBHelper dbHelper = new DBHelper(this.getContext(), "Picture.db", null, 1);
        ArrayList<PictureItem> PicList = dbHelper.recipes_SelectAll();

       for (int i=0; i < PicList.size(); i++)
       {
           Log.d("Picture List : ", String.valueOf(PicList.get(i).get_id() + ", " + PicList.get(i).get_latitude()));
       }
    }
}

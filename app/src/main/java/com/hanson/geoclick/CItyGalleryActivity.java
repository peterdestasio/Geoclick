package com.hanson.geoclick;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.hanson.geoclick.Adapters.CityGalleryAdapter;
import com.hanson.geoclick.Helper.DBHelper;
import com.hanson.geoclick.Model.PictureItem;

import java.util.ArrayList;

/*
  This activity use a RecycleView to display efficiently a list of images related by cities
  (For reference of how it works recycle view see GalleryFragment)
  It is called by GalleryFragment and MapViewFragment
 */

public class CItyGalleryActivity extends AppCompatActivity {

    DBHelper dbHelper = new DBHelper(this, "Picture.db", null, 1);
    ArrayList<PictureItem> picCities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_gallery);

        Intent intent = getIntent();
        String city = intent.getStringExtra("cityChoise");

        picCities = dbHelper.selectPicFromCity(city);

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.imagegallery);
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(),2);
        recyclerView.setLayoutManager(layoutManager);

        CityGalleryAdapter adapter = new CityGalleryAdapter(getApplicationContext(), picCities);
        recyclerView.setAdapter(adapter);
    }



}

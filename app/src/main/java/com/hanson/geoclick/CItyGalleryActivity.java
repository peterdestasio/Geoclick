package com.hanson.geoclick;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;

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
    String city;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_gallery);

        Intent intent = getIntent();
        city = intent.getStringExtra("cityChoise");

        //Show backbutton
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        //Set page title color
        Spannable text = new SpannableString(actionBar.getTitle());
        text.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorAccent)), 0, text.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        actionBar.setTitle(text);



    }

    @Override
    protected void onStart() {
        super.onStart();
        picCities = dbHelper.selectPicFromCity(city);

       if(picCities.size() == 0){
            finish();
        }
        else {


            RecyclerView recyclerView = (RecyclerView)findViewById(R.id.imagegallery);
            recyclerView.setHasFixedSize(true);

            RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(),2);
            recyclerView.setLayoutManager(layoutManager);

            CityGalleryAdapter adapter = new CityGalleryAdapter(getApplicationContext(), picCities);
            recyclerView.setAdapter(adapter);
        }

        dbHelper.close();


    }



    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}

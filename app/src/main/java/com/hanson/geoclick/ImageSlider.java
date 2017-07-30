package com.hanson.geoclick;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.hanson.geoclick.Adapters.MyImageAdapter;
import com.hanson.geoclick.Helper.DBHelper;

/*
This activity represent a slideshow of a list of pictures, it is called by CityGalleryActivity
It use a widget called ViewPager instantiated through a custom adapter
ViewPager associates each page with a key Object instead of working with Views directly.
This key is used to track and uniquely identify a given page independent of its position in the adapter.
 */

public class ImageSlider extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_slider);

        DBHelper dbHelper = new DBHelper(this, "Picture.db", null, 1);
        Intent intent = getIntent();

        int choise = intent.getIntExtra("idPic", 0);

        ViewPager mViewPager = (ViewPager)findViewById(R.id.viewPageAndroid);
        MyImageAdapter adapterView = new MyImageAdapter(this);
        mViewPager.setAdapter(adapterView);

        //choose the current item inside the image slider
        mViewPager.setCurrentItem(choise);

        dbHelper.close();

    }
}

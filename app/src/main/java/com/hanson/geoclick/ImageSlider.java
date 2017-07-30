package com.hanson.geoclick;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.hanson.geoclick.Adapters.MyImageAdapter;

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

        ViewPager mViewPager = (ViewPager)findViewById(R.id.viewPageAndroid);
        MyImageAdapter adapterView = new MyImageAdapter(this);
        mViewPager.setAdapter(adapterView);

    }
}

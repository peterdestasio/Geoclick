package com.hanson.geoclick;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.hanson.geoclick.Adapters.MyImageAdapter;

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

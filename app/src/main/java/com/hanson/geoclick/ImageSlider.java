package com.hanson.geoclick;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.hanson.geoclick.Adapters.CityGalleryAdapter;
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




//        dbHelper.deleteRow(choise);   odd delete function




        //choose the current item inside the image slider
       mViewPager.setCurrentItem(choise);

        dbHelper.close();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.picture_side_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        /*
             Delete and Share menu buttons
        */

        if (id == R.id.delete) {
            Toast.makeText(getApplicationContext(), "test delete" , Toast.LENGTH_SHORT).show();
            return true;
        }
        if (id == R.id.share) {
            Toast.makeText(getApplicationContext(), "test share" , Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}

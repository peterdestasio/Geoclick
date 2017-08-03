package com.hanson.geoclick;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Toast;

import com.hanson.geoclick.Adapters.CityGalleryAdapter;
import com.hanson.geoclick.Adapters.MyImageAdapter;
import com.hanson.geoclick.Helper.DBHelper;
import com.hanson.geoclick.Model.PictureItem;

import java.util.ArrayList;
import java.util.Calendar;

/*
This activity represent a slideshow of a list of pictures, it is called by CityGalleryActivity
It use a widget called ViewPager instantiated through a custom adapter
ViewPager associates each page with a key Object instead of working with Views directly.
This key is used to track and uniquely identify a given page independent of its position in the adapter.
 */

public class ImageSlider extends AppCompatActivity {

    ViewPager mViewPager;
    DBHelper dbHelper;
    MyImageAdapter adapterView;


    ArrayList<PictureItem> PicList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_slider);

        dbHelper = new DBHelper(this, "Picture.db", null, 1);
        PicList = dbHelper.pictures_SelectAll();

        Intent intent = getIntent();
        int choise = intent.getIntExtra("idPic", 0);

        mViewPager = (ViewPager)findViewById(R.id.viewPageAndroid);
        adapterView = new MyImageAdapter(this);
        mViewPager.setAdapter(adapterView);
        




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
            //Toast.makeText(getApplicationContext(), "test delete" , Toast.LENGTH_SHORT).show();
            deletePic();
            return true;
        }
        if (id == R.id.share) {
            Intent shareIntent;
            String shareTest = "Geoclick App. ";

            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);


            shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.setType("image/png");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My App");
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareTest);
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareTest + year);
            startActivity(Intent.createChooser(shareIntent, "Share Via"));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void deletePic() {
        PictureItem selectPic = (PictureItem) PicList.get(mViewPager.getCurrentItem());

        dbHelper.deleteRow(selectPic.get_id());
        //mViewPager.removeViewAt(mViewPager.getCurrentItem());

        int pageIndex = mViewPager.getCurrentItem();
        mViewPager.removeViewAt(mViewPager.getCurrentItem());
        // You might want to choose what page to display, if the current page was "defunctPage".
        if (pageIndex == adapterView.getCount())
            pageIndex--;
        mViewPager.setCurrentItem(pageIndex);

        if (mViewPager.getChildCount() == 0) {
            Toast.makeText(getApplicationContext(), "Doesn't have Picture", Toast.LENGTH_SHORT).show();
            finish();
        }



    }

}

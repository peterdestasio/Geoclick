package com.hanson.geoclick.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.hanson.geoclick.Helper.DBHelper;
import com.hanson.geoclick.Helper.ImageHelper;
import com.hanson.geoclick.Model.PictureItem;
import com.hanson.geoclick.R;

import java.util.ArrayList;

/*
Customized adapter that extend PagerAdapter it is used by ImageSlider
 */
public class MyImageAdapter extends PagerAdapter {

    private ArrayList<PictureItem> pictureItems;


    Context mContext;

    int choise;


    public MyImageAdapter(Context context)
    {
        this.mContext = context;
        create();

    }

    //function used to inizialize the adapter with the data taken form the database
    public void create(){
        DBHelper dbHelper = new DBHelper(mContext, "Picture.db", null, 1);

        Intent intent = ((Activity)mContext).getIntent();

        String city = intent.getStringExtra("cityChosen");

        choise = intent.getIntExtra("idPic", 0);
        pictureItems = dbHelper.selectPicFromCity(city);

        dbHelper.close();

    }

    //return the size of the array
    @Override
    public int getCount()
    {
        return pictureItems.size();
    }

    /**
     * Determines whether a page View is associated with a specific key object as
     * returned by instantiateItem(ViewGroup, int).
     *
     * @param v   Page View to check for association with object
     * @param obj Object to check for association with view
     *
     * @return true if view is associated with the key object object.
     */
    @Override
    public  boolean isViewFromObject(View v,Object obj){

        return v == ((ImageView) obj);
    }


    /**
     * Create the page for the given position.
     *
     * @param container The containing View in which the page will be shown.
     * @param i The page position to be instantiated.
     *
     * @return Returns an Object representing the new page. This does not need
     *         to be a View, but can be some other container of the page.
     */
    @Override
    public  Object instantiateItem(ViewGroup container, int i)
    {
        //i = choise;
        ImageView mImageView = new ImageView(mContext);
        mImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
       // mImageView.setImageResource(sliderImagesId[i]);
        //mImageView.setImageBitmap(imageHelper.getBitmapFromByteArray(pictureItems.get(i).get_thumbnail()));
        mImageView.setImageURI(Uri.parse(pictureItems.get(i).get_mainImg()));
        ((ViewPager)container).addView(mImageView,0);

        return  mImageView;


    }

    @Override
    public  void destroyItem(ViewGroup container,int i, Object obj){

        ((ViewPager)container).removeView((ImageView) obj);
    }
}

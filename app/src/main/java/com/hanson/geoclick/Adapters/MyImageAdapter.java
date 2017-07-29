package com.hanson.geoclick.Adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.hanson.geoclick.R;

public class MyImageAdapter extends PagerAdapter {

    Context mContext;

    //Array Of Images --> that will be replace from query result for the database
    private  int[] sliderImagesId = new int[]{
            R.drawable.micky,R.drawable.lion,R.drawable.fire,
    };

    public MyImageAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public int getCount()
    {
        return sliderImagesId.length;
    }

    @Override
    public  boolean isViewFromObject(View v,Object obj){

        return v == ((ImageView) obj);
    }

    @Override
    public  Object instantiateItem(ViewGroup container, int i)
    {
        ImageView mImageView = new ImageView(mContext);
        mImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        mImageView.setImageResource(sliderImagesId[i]);
        ((ViewPager)container).addView(mImageView,0);
        return  mImageView;


    }

    @Override
    public  void destroyItem(ViewGroup container,int i, Object obj){

        ((ViewPager)container).removeView((ImageView) obj);
    }
}

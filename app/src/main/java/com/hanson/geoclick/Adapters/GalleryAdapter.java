package com.hanson.geoclick.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hanson.geoclick.CItyGalleryActivity;
import com.hanson.geoclick.Helper.ImageHelper;
import com.hanson.geoclick.Model.CityItem;
import com.hanson.geoclick.R;

import java.util.ArrayList;

/**
 * Created by Pyosnag on 2017. 7. 22..
 */

/*
* This adapter implements the The ViewHolder pattern.
* The ViewHolder pattern holds Views in a cache, when created, and reuses Views from this cache as needed.
*
* While the ListView encourages the use of this pattern, it did not require it, and so, developers could ignore the ViewHolder pattern
* and create a new View every time. RecyclerView forces usage of this pattern.
*
*
 */

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ViewHolder> {

    private  ArrayList<CityItem> cityList;
    private ImageHelper imageHelper = new ImageHelper();

    private Context context;

    //constructor
    public GalleryAdapter(Context context, ArrayList<CityItem> cityList){
        this.cityList = cityList;
        this.context = context;
    }


    //The viewHolder holds Views in a cache, when created, and reuses Views from this cache as needed.
    //This method create the VewHolder by referencing the cell_layout file
    @Override
    public GalleryAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cell_layout, viewGroup, false);
        return new ViewHolder(view);
    }

    /*
     * bind the ViewHolder with the data from our ArrayList of CityItem
     * find the TextView first and set the title of the city then find the ImageView and use the image thumbnail to display
      */
    @Override
    public void onBindViewHolder(final GalleryAdapter.ViewHolder viewHolder, final int i) {
        viewHolder.title.setText(cityList.get(i).getName());
        viewHolder.img.setScaleType(ImageView.ScaleType.CENTER_CROP);
        viewHolder.img.setImageBitmap(imageHelper.getBitmapFromByteArray(cityList.get(i).getThumbnail()));

        //listen the event on a click of an image
        viewHolder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CItyGalleryActivity.class);
                intent.putExtra("cityChoise",cityList.get(i).getName().toString());
                context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return cityList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView title;
        private ImageView img;
        public ViewHolder(View view) {
            super(view);

            title = (TextView)view.findViewById(R.id.title);
            img = (ImageView) view.findViewById(R.id.img);
        }
    }
}
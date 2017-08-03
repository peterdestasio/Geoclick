package com.hanson.geoclick.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hanson.geoclick.Helper.ImageHelper;
import com.hanson.geoclick.ImageSlider;
import com.hanson.geoclick.Model.PictureItem;
import com.hanson.geoclick.R;

import java.util.ArrayList;

/**
 * Created by pierluigidestasio on 2017-07-29.
 * similar to Gallery Adapter but without the title because we want only pictures
 */

public class CityGalleryAdapter extends RecyclerView.Adapter<CityGalleryAdapter.ViewHolder> {

    private ArrayList<PictureItem> pictureItems;
    private ImageHelper imageHelper = new ImageHelper();

    private Context context;


    public CityGalleryAdapter(Context context, ArrayList<PictureItem> pictureItems){
        this.pictureItems = pictureItems;
        this.context = context;
    }


    //The viewHolder holds Views in a cache, when created, and reuses Views from this cache as needed.
    @Override
    public CityGalleryAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.citygallerylayout, viewGroup, false);
        return new CityGalleryAdapter.ViewHolder(view);
    }



    @Override
    public void onBindViewHolder(CityGalleryAdapter.ViewHolder viewHolder, final int i) {
        viewHolder.title.setText("");
        viewHolder.img.setScaleType(ImageView.ScaleType.CENTER_CROP);
        viewHolder.img.setImageBitmap(imageHelper.getBitmapFromByteArray(pictureItems.get(i).get_thumbnail()));

        viewHolder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ImageSlider.class);
                intent.putExtra("idPic",i); //send the id of the image clicked
                Log.e("Chouse", String.valueOf(i));
                intent.putExtra("cityChosen", pictureItems.get(i).get_city()); //send the city chosen
                context.startActivity(intent);
            }
        });
    }



    @Override
    public int getItemCount() {
        return pictureItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView title;
        private ImageView img;
        public ViewHolder(View view) {
            super(view);

            title = (TextView)view.findViewById(R.id.title);
            img = (ImageView) view.findViewById(R.id.img2);
        }
    }
}
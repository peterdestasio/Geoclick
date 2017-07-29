package com.hanson.geoclick.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hanson.geoclick.Helper.ImageHelper;
import com.hanson.geoclick.Model.CityItem;
import com.hanson.geoclick.R;

import java.util.ArrayList;

/**
 * Created by Pyosnag on 2017. 7. 22..
 */

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ViewHolder> {
    //private ArrayList<CreateList> galleryList;
    private  ArrayList<CityItem> cityList;
    private ImageHelper imageHelper = new ImageHelper();

    private Context context;
    Intent shareIntent;
    String shareTest = "Geoclick App. ";
    /*
    public GalleryAdapter(Context context, ArrayList<CreateList> galleryList) {
        this.galleryList = galleryList;
        this.context = context;
    }
    */

    public GalleryAdapter(Context context, ArrayList<CityItem> cityList){
        this.cityList = cityList;
        this.context = context;
    }


    //The viewHolder holds Views in a cache, when created, and reuses Views from this cache as needed.
    @Override
    public GalleryAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cell_layout, viewGroup, false);
        return new ViewHolder(view);
    }
    /*
    //TO FIXXXX
    @Override
    public void onBindViewHolder(GalleryAdapter.ViewHolder viewHolder, final int i) {
        viewHolder.title.setText(galleryList.get(i).getImage_title());
        viewHolder.img.setScaleType(ImageView.ScaleType.CENTER_CROP);
        //viewHolder.img.setImageResource(Integer.parseInt((galleryList.get(i).getImage_ID())));
        viewHolder.img.setImageResource((galleryList.get(i).getImage_ID()));


        viewHolder.img.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
//                Toast.makeText(context,"Title is: " + galleryList.get(i).getImage_title(),Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, CItyGalleryActivity.class);
                context.startActivity(intent);
                return false;
            }
        });

//         sharing to contacts if double click
        viewHolder.img.setOnClickListener(new DoubleClickListener() {

            @Override
            public void onSingleClick(View v) {

            }

            @Override
            public void onDoubleClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);

                Toast.makeText(context,"Double Click!",Toast.LENGTH_SHORT).show();
                shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("image/png");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My App");
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareTest + year);
                context.startActivity(Intent.createChooser(shareIntent,"Share Via"));

            }
        });
    }

    */

    @Override
    public void onBindViewHolder(GalleryAdapter.ViewHolder viewHolder, int i) {
        viewHolder.title.setText(cityList.get(i).getName());
        //viewHolder.title.setText(galleryList.get(i).getImage_title());
        viewHolder.img.setScaleType(ImageView.ScaleType.CENTER_CROP);
        //viewHolder.img.setImageResource(cityList.get(i).getImage_id());
        //viewHolder.img.setImageBitmap(cityList.get(i).getThumbnail());

        viewHolder.img.setImageBitmap(imageHelper.getBitmapFromByteArray(cityList.get(i).getThumbnail()));
    }

    //TO FIXXXX i need thumbnail and image

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
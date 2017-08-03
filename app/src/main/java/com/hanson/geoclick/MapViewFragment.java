package com.hanson.geoclick;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.hanson.geoclick.Helper.DBHelper;
import com.hanson.geoclick.Helper.ImageHelper;
import com.hanson.geoclick.Model.PictureItem;

import java.util.ArrayList;


/*
This Fragment show a list of pictures on a map based on the position in which the pictures were taken
It use Google Maps API
 */

public class MapViewFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    ImageHelper imgeHelper = new ImageHelper();

    ArrayList<PictureItem> PicList;

    ArrayList<PictureItem> picCities;

    public MapViewFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_map_view, container, false);

        // Get the SupportMapFragment and request notification
        // when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment)getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        //Connect with databases
        DBHelper dbHelper = new DBHelper(this.getContext(), "Picture.db", null, 1);
        PicList = dbHelper.pictures_SelectAll();

       for (int i=0; i < PicList.size(); i++)
       {
           Log.d("Picture List : ", String.valueOf(PicList.get(i).get_id() + ", " + PicList.get(i).get_latitude()));
       }

       dbHelper.close();

    }

    //Method that handle the position on the map
    public void onMapReady(final GoogleMap googleMap) {


        for(int i=0;i<PicList.size();i++)
        {
            LatLng place = new LatLng(Double.valueOf(PicList.get(i).get_latitude()),Double.valueOf(PicList.get(i).get_longitude()));

            //Add by Miju Jang - for making small size icon
            Bitmap smallMarker = Bitmap.createScaledBitmap(imgeHelper.getBitmapFromByteArray(PicList.get(i).get_thumbnail()), 70, 70, false);
            googleMap.addMarker(new MarkerOptions().position(place)
                    .title(PicList.get(i).get_city())).setIcon(BitmapDescriptorFactory.fromBitmap(smallMarker));


            ///try!!!
            //bitmapdescriptiorfacory.fromFile(PicList.get(i).get_mainImg()
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(place));

            // Set a listener for marker click.
            googleMap.setOnMarkerClickListener(this);
            //this listener is listening the events that you click on the title of the map marker
            googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                @Override
                public void onInfoWindowClick(Marker marker) {

                    DBHelper dbHelper = new DBHelper(getContext(), "Picture.db", null, 1);
                    picCities = dbHelper.selectPicFromCity(marker.getTitle());


                    //send data to citygallery activity
                    Intent intent = new Intent(getContext(), CItyGalleryActivity.class);
                    intent.putExtra("cityChoise",picCities.get(0).get_city().toString());
                    startActivity(intent);

                    dbHelper.close();

                }
            });
        }


    }

    /** Called when the user clicks a marker. */
    @Override
    public boolean onMarkerClick(final Marker marker) {

        // Retrieve the data from the marker.
        Integer clickCount = (Integer) marker.getTag();
        Log.e("click","clack");


        /*
        // Check if a click count was set, then display the click count.
        if (clickCount != null) {
            clickCount = clickCount + 1;
            marker.setTag(clickCount);
            Toast.makeText(getContext(),
                    marker.getTitle() +
                            " has been clicked " + clickCount + " times.",
                    Toast.LENGTH_SHORT).show();


        }
        */

        // Return false to indicate that we have not consumed the event and that we wish
        // for the default behavior to occur (which is for the camera to move such that the
        // marker is centered and for the marker's info window to open, if it has one).
        return false;
    }

}

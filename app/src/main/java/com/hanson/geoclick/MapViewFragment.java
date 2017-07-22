package com.hanson.geoclick;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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


public class MapViewFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    ImageHelper imgeHelper = new ImageHelper();
    ArrayList<LatLng> samplearray = new ArrayList<>();

    ArrayList<PictureItem> PicList;

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

        //test
        LatLng f = new LatLng(51.507,0.127);
        LatLng g = new LatLng(53.507,0.127);

        samplearray.add(f);
        samplearray.add(g);


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        //Connect with databases
        DBHelper dbHelper = new DBHelper(this.getContext(), "Picture.db", null, 1);
        PicList = dbHelper.recipes_SelectAll();

       for (int i=0; i < PicList.size(); i++)
       {
           Log.d("Picture List : ", String.valueOf(PicList.get(i).get_id() + ", " + PicList.get(i).get_latitude()));
       }

    }

    //Method that handle the position on the map
    public void onMapReady(GoogleMap googleMap) {


        for(int i=0;i<PicList.size();i++)
        {
            LatLng place = new LatLng(Double.valueOf(PicList.get(i).get_latitude()),Double.valueOf(PicList.get(i).get_longitude()));

            googleMap.addMarker(new MarkerOptions().position(place)
                    .title(PicList.get(i).get_city())).setIcon(BitmapDescriptorFactory.fromBitmap(imgeHelper.getBitmapFromByteArray(PicList.get(i).get_thumbnail())));

            //bitmapdescriptiorfacory.fromFile(PicList.get(i).get_mainImg()
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(place));

            // Set a listener for marker click.
            googleMap.setOnMarkerClickListener(this);
            googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                @Override
                public void onInfoWindowClick(Marker marker) {
                    Intent intent = new Intent(getContext(), CItyGalleryActivity.class);
                    startActivity(intent);

                }
            });
        }


        // Add a marker in Sydney, Australia,
        // and move the map's camera to the same location.
        //LatLng sydney = new LatLng(-33.852, 151.211);
//        LatLng sydney = new LatLng(-33.852, 151.211);
//        googleMap.addMarker(new MarkerOptions().position(sydney)
//                .title("Sydney")).setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher));
//        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
//
//        // Set a listener for marker click.
//        googleMap.setOnMarkerClickListener(this);
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
